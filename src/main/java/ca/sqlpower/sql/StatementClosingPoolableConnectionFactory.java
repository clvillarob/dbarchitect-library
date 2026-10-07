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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingConnection;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.log4j.Logger;

/**
 * Extends the orginal factory to return fancy new 
 * PoolableStatementClosingConnections instead of the boring 
 * old PoolableConnections.
 * 
 * @author dfraser
 * @version $Id$
 */
public class StatementClosingPoolableConnectionFactory extends PoolableConnectionFactory {

	private static final Logger logger
		= Logger.getLogger(StatementClosingPoolableConnectionFactory.class);

	/**
	 * Default superclass constructor.
	 *
	 * @throws Exception When there is an error in the PoolableConnectionFactory constructor.
	 */
	public StatementClosingPoolableConnectionFactory(ConnectionFactory factory,
													 ObjectPool pool,
													 KeyedObjectPoolFactory stmtPoolFactory,
													 String validationQuery,
													 boolean defaultReadOnly,
													 boolean defaultAutoCommit)
		throws Exception {

		super(factory, pool, stmtPoolFactory, validationQuery,
			  defaultReadOnly, defaultAutoCommit);
	}

   
	/**
	 * Creates a new connection using the connFactory, then sets its
	 * autoCommit and readOnly values to the settings on this
	 * factory.
	 */
	synchronized public Object makeObject() throws Exception {
        Connection con = _connFactory.createConnection();
        try {
            con.setAutoCommit(_defaultAutoCommit);
        } catch (SQLException e) {
            logger.error("Couldn't set autoCommit to "+_defaultAutoCommit+"!", e);
			// continue anyway
        }

		if (!DBConnection.isOracle(con)) {
			// Oracle doesn't need this, and in fact doesn't support it either
			try {
				con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			} catch (SQLException e) {
				logger.error("Couldn't set transactionIsolation to TRANSACTION_READ_UNCOMMITTED!", e);
				// continue anyway
			}
		}

        try {
            con.setReadOnly(_defaultReadOnly);
        } catch (SQLException e) {
            logger.error("Couldn't set readOnly to "+_defaultReadOnly+"!", e);
			// continue anyway
        }

        if (_stmtPoolFactory != null) {
            KeyedObjectPool stmtpool = _stmtPoolFactory.createPool();
            con = new PoolingConnection(con,stmtpool);
            stmtpool.setFactory((PoolingConnection)con);
        }
		
		// check for requisite version of PL schema
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select schema_version from def_param");
			if(!rs.next()) {
				throw new SQLException("def_param table has no rows");
			}
			
			// the schema version check used to be here.
			// it's moved to a static attribute of the Dashboard class.
			// it should be right near the top of the Dashboard.java file.
			// thank you for your interest!  have a nice day.
			//             -- Dan
			

		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}

        return new PoolableStatementClosingConnection(con, _pool);
    }
}


