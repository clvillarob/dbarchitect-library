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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;

public class DBConnection {

    /**
     * This class cannot be instantiated
     */
    private DBConnection()
    {
    }

	/**
	 * Parses the WEB-INF/databases.ini file into the given lists.
	 *
	 * @deprecated Use the <code>getAvailableDatabases()</code> method
	 * to retrieve the set of available databases.
	 */
	public static String readINIFile(ServletContext servletContext,
									 String match_db_name,
									 StringBuffer db_url,
									 StringBuffer db_class,
									 ArrayList db_names) {
		return "Available databases are no longer specified by this method.";
	}
	
    /**
     * Checks if this connection refers to an Oracle database.
     *
     * @return True if Oracle, otherwise False
     */
	public static boolean isOracle(Connection con) {
		DatabaseMetaData dmd;
		String url="";
		
		try {
			dmd = con.getMetaData();
			url = dmd.getURL();
		} catch(SQLException e) {
			System.out.println("problem in DBConnection.isOracle: "+e.getMessage());
		}

		if(url.toUpperCase().indexOf("ORACLE") == -1){
			return false;
		} else {
			return true;
		}
	}

	
    /**
     * Checks if this connection refers to a PostgreSQL database.
     *
     * @return True if postgresql, otherwise False
     */
	public static boolean isPostgres(Connection con) {
		DatabaseMetaData dmd;
		
		try {
			dmd = con.getMetaData();
			if(dmd.getDatabaseProductName().indexOf("PostgreSQL") >= 0) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
			System.out.println("problem in DBConnection.isPostgres: "+e.getMessage());
		}
		return false;
	}

    /**
     * Checks if this connection refers to a Microsoft SQLServer database.
     *
     * @return True if SQLServer, otherwise False
     */
	public static boolean isSQLServer(Connection con) {
		DatabaseMetaData dmd;
		
		try {
			dmd = con.getMetaData();
			if(dmd.getDatabaseProductName().indexOf("Microsoft SQL Server") >= 0) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
			System.out.println("problem in DBConnection.isSQLServer: "+e.getMessage());
		}
		return false;
	}

    /**
     * Checks if this connection refers to a IBM DB2 database.
     *
     * @return True if DB2, otherwise False
     */
	public static boolean isDB2(Connection con) {
		DatabaseMetaData dmd;
		
		try {
			dmd = con.getMetaData();
			if(dmd.getDatabaseProductName().indexOf("DB2") >= 0) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
			System.out.println("problem in DBConnection.isDB2: "+e.getMessage());
		}
		return false;
	}

	public static String getSystemDate(Connection con) {
		String systemDate="";
		
		if(isOracle(con)){
			systemDate = "SYSDATE";
		} else if (isPostgres(con)) {
			systemDate = "now()";
		} else {
			systemDate = "{fn NOW()}";
		}
		
		return systemDate;
	}
	
	
	public static String getUser(Connection con)
		throws SQLException
	{
		String user = null;
		if (con instanceof PoolableStatementClosingConnection) {
			user = ((PoolableStatementClosingConnection) con).getPlUsername();
		}

		if (user != null) return user;

		DatabaseMetaData dmd = con.getMetaData();
		user = dmd.getUserName();

		return user;
	}
	
	
	public static String ifNull(String valueString,
								String ifNullString)
	{
		String newValueString;
		
		if(valueString==null || valueString.equals("")){
			newValueString=ifNullString;
		} else {
			newValueString = valueString;
		}
		
		return newValueString;
	} 
}
