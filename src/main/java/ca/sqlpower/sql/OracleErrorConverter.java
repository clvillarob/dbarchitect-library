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
 * Converts SQLExceptions from Oracle into SQLPower error numbers.
 */
public class OracleErrorConverter extends AbstractErrorConverter {

	/**
	 * @see ca.sqlpower.sql.AbstractErrorConverter#convert(SQLException)
	 */
	public int convert(SQLException e) {
		switch (e.getErrorCode()) {
		case 904: 
			return UNKNOWN_COLUMN;
		case 917:
			return SQL_SYNTAX_ERROR;
		case 942:
			return UNKNOWN_COLUMN;
		case 1017:
			return INVALID_LOGON;
		case 17443:
			return INVALID_LOGON;
		case 17002:
			return SERVER_UNAVAILABLE;
		case 17006:
			return UNKNOWN_COLUMN;
		default :
			return UNKNOWN_ERROR;
		}
	}
}

