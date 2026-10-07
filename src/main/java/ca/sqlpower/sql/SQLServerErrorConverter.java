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
 * Converts SQLExceptions from SQL Server into SQLPower error numbers.
 */
public class SQLServerErrorConverter extends AbstractErrorConverter {
	
	public int convert(SQLException e) {
		switch (e.getErrorCode()) {
		case 0:
			// this might be a problem: we get error code "0" for
			// "error establishing the socket," which is likely not
			// unique.
			return SERVER_UNAVAILABLE;

		case 207:
			return UNKNOWN_COLUMN;
		case 208:
			return UNKNOWN_TABLE;
		case 18456:
		case 4060:
		case 18452:
			return INVALID_LOGON;

		default:
			return UNKNOWN_ERROR;
		}
	}
}
