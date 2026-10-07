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
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.pool.ObjectPool;

/**
 * Extension to the Apache Commons DBCP PoolableConnection that keeps
 * a list of all statements that it has given out and closes them all 
 * when its close method is called. When it closes statements, it ignores 
 * SQLExceptions, since the
 * statements could have (and should have) already been closed by
 * the outside world.
 * 
 * @author Dan Fraser
 * @version $Id$
 */
public class PoolableStatementClosingConnection extends PoolableConnection {

	List openStatements = java.util.Collections.synchronizedList(new LinkedList());

	/**
	 * Useful storage place for single-database-user connection
	 * pooling products, because the DatabaseMetaData will give back
	 * the master user's name rather than the current user's name.  If
	 * your product uses this, you have to set it up yourself.
	 */
	protected String plUsername;

	/**
	 * Just call the superclass constructor
	 * @param arg0
	 * @param arg1
	 */
	public PoolableStatementClosingConnection(
											  Connection arg0,
											  ObjectPool arg1) {
		super(arg0, arg1);
	}


	/**
	 * Creates a new java.sql.Statement, adds it to the list of open statements, 
	 * and returns it.
	 */
	public Statement createStatement() throws SQLException {
		Statement stmt = new ResultSetClosingStatement(this, super.createStatement());
		openStatements.add(stmt);
		return stmt;
	}
	
	/**
	 * Iterates over the list of open statements and calls close() on each one, 
	 * ignoring any resulting SQLExceptions.
	 */
	public synchronized void close() throws SQLException {
		Iterator stmtIt = openStatements.iterator();
		while (stmtIt.hasNext()) {
			Statement stmt = (Statement) stmtIt.next();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// statement probably already closed
				}
			}
		}

		openStatements.clear();
		super.close();
	}

	/**
	 * Gets the value of plUsername
	 *
	 * @return the value of plUsername
	 */
	public String getPlUsername()  {
		return this.plUsername;
	}

	/**
	 * Sets the value of plUsername
	 *
	 * @param argPlUsername Value to assign to this.plUsername
	 */
	public void setPlUsername(String argPlUsername) {
		this.plUsername = argPlUsername;
	}
}
