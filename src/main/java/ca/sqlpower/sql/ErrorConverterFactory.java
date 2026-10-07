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
 * Selects the appropriate error converter based on database type.
 */

public class ErrorConverterFactory {

	private static PLErrorConverter plErrorConverter = new PLErrorConverter();
	private static OracleErrorConverter oracleErrorConverter = new OracleErrorConverter();
	private static SQLServerErrorConverter sqlServerErrorConverter = new SQLServerErrorConverter();
	private static PostgreSQLErrorConverter pgErrorConverter = new PostgreSQLErrorConverter();

	/**
	 * Returns a reference to a error converter object based on the
	 * databaseProductName attribute of the connection specified.
	 *
	 * @throws IllegalArgumentException if the database type is unrecognized.
	 */
	public static AbstractErrorConverter getInstance(SQLException e) {
		String message = e.getMessage();

		if (message.indexOf("PLSchemaException") >= 0) {
			return plErrorConverter;
		} else if (message.indexOf("icrosoft") >= 0) {
			return sqlServerErrorConverter;
		} else if (message.indexOf("Backend") >= 0
				   || message.indexOf("ERROR: ") >= 0
				   || message.indexOf("FATAL:") >= 0) {
			return pgErrorConverter;
		} else if (message.startsWith("ORA")
				   || message.indexOf("THIN") >= 0
				   || message.indexOf("Io exception: The Network Adapter") >= 0
				   || message.indexOf("invalid arguments in call") >= 0
				   || message.indexOf("Invalid column name") >= 0
				   || message.indexOf("(ERROR=(") >= 0) {
			return oracleErrorConverter;
		}
		throw new IllegalArgumentException("unrecognized database type for message: "+message);
	}

}

