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

import java.sql.SQLException;

/**
 * Handles the conversion of database-specific error numbers within 
 * SQLExceptions to custom SQLPower error numbers.
 */
public abstract class AbstractErrorConverter {

	/**
	 * Used if database connection fails because of invalid
	 * username or password.
	 */
	public static final int INVALID_LOGON = 1;

	/**
	 * Used if a query fails due to a missing or unknown column name.
	 */
	public static final int UNKNOWN_COLUMN = 2;

	/**
	 * Used if the driver can't connect to the database server at all.
	 */
	public static final int SERVER_UNAVAILABLE = 3;

	/**
	 * Used if the PLErrorConverter is asked to convert an exception.
	 * Means the schema is too old or just plain missing.
	 */
	public static final int BAD_PL_SCHEMA = 4;

	/**
	 * Used if the DMBS or JDBC driver throws an exception due to a
	 * syntax error in a SQL query.
	 */
	public static final int SQL_SYNTAX_ERROR = 5;

	/**
	 * Used for RDBMS unknown table/view errors.
	 */
	public static final int UNKNOWN_TABLE = 6;

	/** 
	 * Used if an appropriate conversion for the database error
	 * cannot be found.
	 */
	public static final int UNKNOWN_ERROR = -1;
	
	/**
	 * Converts a SQLException to a SQLPower error code.
	 */
	public abstract int convert(SQLException e);

}
