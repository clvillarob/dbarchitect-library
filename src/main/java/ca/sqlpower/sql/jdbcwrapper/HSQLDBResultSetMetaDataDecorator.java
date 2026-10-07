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

package ca.sqlpower.sql.jdbcwrapper;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class HSQLDBResultSetMetaDataDecorator extends ResultSetMetaDataDecorator {

	public HSQLDBResultSetMetaDataDecorator(ResultSetMetaData rsmd) {
		super(rsmd);
	}
	
	@Override
	public String getColumnLabel(int column) throws SQLException {
		String label = super.getColumnLabel(column);
		if (label == null || label.equals("")) {
			return getColumnName(column);
		} else {
			return label;
		}
	}
	
	@Override
	public String getColumnName(int column) throws SQLException {
		String name = super.getColumnName(column);
		if (name == null || name.equals("")) {
			return "COL_" + column;
		} else {
			return name;
		}
	}
}
