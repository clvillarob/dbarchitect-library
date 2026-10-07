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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class OraclePreparedStatementDecorator extends
		PreparedStatementDecorator implements PreparedStatement {

	public OraclePreparedStatementDecorator(
			ConnectionDecorator parentConnection, PreparedStatement ps) {
		super(parentConnection, ps);
	}

	@Override
	protected ResultSet makeResultSetDecorator(ResultSet rs) {
		return new OracleResultSetDecorator(this, rs);
	}

	@Override
	protected ResultSetMetaData makeResultSetMetaDataDecorator(
			ResultSetMetaData rsmd) {
		return new OracleResultSetMetaDataDecorator(rsmd);
	}

	/**
	 * Oracle doesn't recognize the Boolean type, so translate it to
	 * something it does, Bit.
	 * 
	 */
	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		int type = sqlType;
		
		if (sqlType == Types.BOOLEAN) {
			type = Types.BIT;
		}
		super.setNull(parameterIndex, type);
	}

	/**
	 * Oracle doesn't recognize the Boolean type, so translate it to
	 * something it does, Bit.
	 * 
	 */
	@Override
	public void setNull(int paramIndex, int sqlType, String typeName)	throws SQLException {
		int type = sqlType;
		
		if (sqlType == Types.BOOLEAN) {
			type = Types.BIT;
		}
		
	    super.setNull(paramIndex, type, typeName);
	}
	
	
	

}
