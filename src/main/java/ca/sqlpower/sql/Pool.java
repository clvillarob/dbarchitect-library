/*
 * This file is part of DBArchitect.
 *
 * Copyright (c) 2026 villasoft (villasoft.cl@gmail.com)
 *
 * DBArchitect is a fork of Power*Architect, originally developed and
 * copyrighted by SQL Power Group Inc. (Copyright (c) 2008-2010).
 *
 * DBArchitect is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * DBArchitect is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.sqlpower.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Connection pool wrapper class, providing a simple connection pool interface
 * to SQL Power applications without exposing the underlying pool's interface.
 * 
 * In this case, it is implemented using Apache Commons DBCP, plus some
 * customized classes such as the ca.sqlpower.sql.PoolableStatementClosingConnection
 * and the ca.sqlpower.sql.StatementClosingPoolableConnectionFactory.
 * 
 * @author Dan Fraser
 * @version $Id$
 */
public class Pool {

	private static Map pools = new HashMap();	
	private static Map objPools = new HashMap(); // for debugging and stats tracking
	private DBConnectionSpec dbcs;
	private String poolName;
	private PoolingDataSource dataSource;
	ObjectPool connectionPool;
	
	/** 
	 * Prepares a connection pool for the specified DBConnectionSpec. Adds
	 * the pool to a cache of pools for re-use whenever the DBConnectionSpec,
	 * username, and password are re-used. This essentially creates one pool
	 * per unique database/user combination.
	 */
	public Pool(DBConnectionSpec dbcs) throws Exception {
		this.dbcs = dbcs;
        poolName =  dbcs.getUrl()+"-"+dbcs.getUser()+"-"+dbcs.getPass();
        dataSource = (PoolingDataSource) pools.get(poolName);
        connectionPool = (ObjectPool) objPools.get(poolName); // for debugging
        
        if (dataSource == null) {
        	GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
  
			// XXX: this should come from a properties file.
        	poolConfig.maxActive = 100;
        	poolConfig.maxIdle = 50;
        	poolConfig.maxWait = 10000;
        	poolConfig.minEvictableIdleTimeMillis = 1000*60*5;
        	poolConfig.timeBetweenEvictionRunsMillis = 10000;
        	poolConfig.numTestsPerEvictionRun = 5;
			poolConfig.testOnBorrow = true;


		    connectionPool = new GenericObjectPool(null,poolConfig);

		    ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(dbcs.getUrl(),dbcs.getUser(),dbcs.getPass());
        	PoolableConnectionFactory poolableConnectionFactory = new StatementClosingPoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);
			poolableConnectionFactory.setValidationQuery("select 1 from def_param");
	        this.dataSource = new PoolingDataSource(connectionPool);
	        pools.put(poolName, dataSource);	
	        objPools.put(poolName, connectionPool);   // for debugging      
        } else {
			// found connection pool in cache
        }
	}
		
	/**
	 * Returns a Connection from the pool.  Be sure to close it when you're done 
	 * with it!
	 *
	 * <p>Note: if the getConnection() call throws a SQLException,
	 * this method intercepts it and removes this connection pool from
	 * the cache.  This is important because we don't want to keep
	 * pools of connections with invalid username/password
	 * combinations!
	 */
	public Connection getConnection() throws SQLException {
		Connection con;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			pools.remove(poolName);
			objPools.remove(poolName);
			throw e;
		}
		return con;
	}		
}
