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

import java.sql.Types;

/**
 * This class helps with SQL syntax when creating and altering columns
 * in SQLServer.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class SQLServerTypeConverter extends SqlTypeConverter {

	/**
	 * Overrides the superclass's convertType method where required by
	 * SqlServer syntax.
	 */
	public String convertType(int sqlType, int precision, int scale) {
		switch (sqlType) {

		case Types.SMALLINT:
			return "SMALLINT";

		case Types.INTEGER:
			return "INTEGER";

		case Types.FLOAT:
			return "REAL";

		case Types.DOUBLE:
		case Types.REAL:
			return "DOUBLE";

		case Types.NUMERIC:
			if (precision > 0 && scale > 0) {
				return "NUMERIC("+precision+","+scale+")";
			} else if (precision > 0) {
				return "NUMERIC("+precision+")";
			} else {
				return "NUMERIC";
			}

		case Types.TIMESTAMP:
			return "DATETIME";

		default:
			return super.convertType(sqlType, precision, scale);
		}
	}
}
