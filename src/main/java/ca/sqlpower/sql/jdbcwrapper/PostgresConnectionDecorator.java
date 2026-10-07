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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The PostgresConnectionDecorator makes sure that the special PostgresDatabaseMetaDataDecorator
 * class wraps the database metadata returned by the PostgreSQL driver.
 *
 * @author fuerth
 * @version $Id$
 */
public class PostgresConnectionDecorator extends ConnectionDecorator {
    
    /**
     * Creates a new PostgresConnectionDecorator.
     * 
     * @param delegate an instance of the PostgreSQL Connection object.
     */
    public PostgresConnectionDecorator(Connection delegate) {
        super(delegate);
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
    	
    	if (databaseMetaDataDecorator == null) {
    		databaseMetaDataDecorator = new PostgresDatabaseMetaDataDecorator(super.getMetaData(), this);
    	}
    	
        return databaseMetaDataDecorator;
    }

	@Override
	protected PreparedStatement makePreparedStatementDecorator(
			PreparedStatement pstmt) {
		return new GenericPreparedStatementDecorator(this, pstmt);
	}

	@Override
	protected Statement makeStatementDecorator(Statement stmt) {
		return new GenericStatementDecorator(this, stmt);
	}
}
