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

import ca.sqlpower.sqlobject.SQLType;
import java.sql.Connection;
import java.sql.Types;

/**
 * A class that converts JDBC result set metadata sql types into names
 * that can be used to create and alter columns in a specific
 * database.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public abstract class SqlTypeConverter {
	public static SqlTypeConverter getInstance(Connection con) {
		if (con.getClass().getName().indexOf("postgresql") >= 0) {
			return new PostgreSQLTypeConverter();
		} else if (con.getClass().getName().indexOf("SQLServer") >= 0) {
			return new SQLServerTypeConverter();
		} else {
			throw new UnsupportedOperationException("Unknown database type for driver "
													+con.getClass().getName());
		}
	}

	/**
	 * A generic type converter.  May not work with any database at
	 * all, but provides (what I hope is) a maximally standard set of
	 * defaults.  This way, subclasses will be kept as small as
	 * possible.
	 */
	public String convertType(int sqlType, int precision, int scale) {
		switch (sqlType) {
			
		case Types.CHAR:
			return "CHARACTER("+precision+")";

		case SQLType.NCHAR:
			return "NCHAR("+precision+")";
			
		case Types.DATE:
			return "DATE";

		case Types.DECIMAL:
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			return "DECIMAL("+precision+")";

		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.NUMERIC:
		case Types.REAL:
			return "NUMBER("+precision+","+scale+")";

		case Types.TIME:
			return "TIME";

		case Types.TIMESTAMP:
			return "TIMESTAMP";

		case Types.VARCHAR:
			return "VARCHAR("+precision+")";

		case SQLType.NVARCHAR:
			return "NVARCHAR("+precision+")";

			// we don't try to provide generic defaults for these types
		case Types.ARRAY:
		case Types.BIGINT:
		case Types.BINARY:
		case Types.BIT:
		case Types.BLOB:
			//case Types.BOOLEAN: JDBC 3
		case Types.CLOB:
			//case Types.DATALINK: JDBC 3
		case Types.DISTINCT:
		case Types.JAVA_OBJECT:
		case Types.LONGVARBINARY:
		case Types.LONGVARCHAR:
		case Types.NULL:
		case Types.OTHER:
		case Types.REF:
		case Types.STRUCT:
		case Types.VARBINARY:
		default:
			throw new UnsupportedOperationException
				("SQL Type "+sqlType+" is not implemented for this database.");
		}
	}
}
