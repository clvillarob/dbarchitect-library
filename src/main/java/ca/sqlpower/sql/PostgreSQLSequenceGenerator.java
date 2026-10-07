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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLSequenceGenerator extends SequenceGenerator {
    
    private Connection con;
    
    public PostgreSQLSequenceGenerator(Connection con) {
        super();
        this.con = con;
    }

    /**
     * Retrieves a unique long integer value from the specified
     * PostgreSQL sequence.
     *
     * @param con A connection to a PostgreSQL database.
     * @param sequenceTable The name of a PostgreSQL sequence to use.
     * @return A long integer n such than n has never been returned
     * for this sequenceTable and will never again be returned for
     * this sequenceTable.
     * @throws SQLException if a database error occurs.
     */
    public long nextLong(String sequenceTable) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT nextval(").append(SQL.quote(sequenceTable)).append(")");

        Statement stmt = null;
        long nextval;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            if (!rs.next()) {
                throw new SQLException("The sequence returned nothing!");
            }
            nextval = rs.getLong(1);
            rs.close();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return nextval;
    }

    /**
     * Closes the connection that was passed to the constructor.  You
     * should probably close it yourself rather than calling this
     * method.
     */
    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
