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

import org.apache.log4j.Logger;

public class SQLServerSequenceGenerator extends SequenceGenerator {

	private static final Logger logger = Logger.getLogger(SQLServerSequenceGenerator.class);
	
	private Connection con;
	
	public SQLServerSequenceGenerator(Connection con) {
		super();
		this.con = con;
	}
	
	@Override
	public long nextLong(String sequenceTable) throws SQLException {
		StringBuffer selectSql = new StringBuffer();
        selectSql.append("SELECT currval FROM ").append(SQL.escapeStatement(sequenceTable)).append(";");
        long nextval;
        logger.debug("Sequence Generator select SQL statement is: " + selectSql);
        
        Statement stmt = null;
        int oldTransactionIsolation;
        oldTransactionIsolation = con.getTransactionIsolation();
        try {
        	con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql.toString());
            if (!rs.next()) {
                throw new SQLException("The sequence returned nothing!");
            }
            nextval = rs.getLong(1)+1;
            
            StringBuffer updateSql = new StringBuffer();
            updateSql.append("UPDATE ").append(SQL.escapeStatement(sequenceTable));
            updateSql.append(" SET currval=" + nextval);
            logger.debug("Sequence Generator update SQL statement is: " + updateSql);
            int updateRS = stmt.executeUpdate(updateSql.toString());
            if (updateRS == 0) {
                throw new SQLException("No rows were updated. Serializability should prevent this.");
            }
            
            rs.close();
        } finally {
        	con.setTransactionIsolation(oldTransactionIsolation);
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
