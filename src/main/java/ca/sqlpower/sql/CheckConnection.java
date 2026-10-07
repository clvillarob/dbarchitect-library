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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class CheckConnection {

    /**
     * Checks a databases.xml connection to make sure it's working.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("CheckConnection: A small utility to test a databases.xml connection");
            System.out.println("Usage: ");
            System.out.println("  java -cp sqlpower.jar:/path/to/jdbc/driver.jar ca.sqlpower.sql.CheckConnection /path/to/databases.xml");
            return;
        }
        String databasesXMLPath = args[0];
        
        System.out.println("Reading connection specs from "+databasesXMLPath);
        System.out.println("");
        DBCSSource dbcss = new XMLFileDBCSSource(databasesXMLPath);
        List dbcsList = dbcss.getDBCSList();
        Iterator it = dbcsList.iterator();
        int i = 0;
        while (it.hasNext()) {
            DBConnectionSpec dbcs = (DBConnectionSpec) it.next();
            System.out.println("["+i+"] "+dbcs.getDisplayName()+" <"+dbcs.getUrl()+">");
            i++;
        }
        System.out.print("Test which connection? ");
        String conNum = readLine();
        
        DBConnectionSpec dbcs = (DBConnectionSpec) dbcsList.get(Integer.parseInt(conNum.toString()));
        if (dbcs.getUser() == null || dbcs.getPass() == null) {
            System.out.print("Username? ");
            dbcs.setUser(readLine());
            System.out.print("Password? ");
            dbcs.setPass(readLine());
        }
        
        System.out.println("Making connection...");
        System.out.println("Connection Name:   "+dbcs.getDisplayName());
        System.out.println("JDBC Driver Class: "+dbcs.getDriverClass());
        Class.forName(dbcs.getDriverClass(), true, CheckConnection.class.getClassLoader()).newInstance();
        System.out.println("JDBC URL:          "+dbcs.getUrl());
        System.out.println("Username:          "+dbcs.getUser());
        System.out.println("Password:          "+dbcs.getPass());
        
        long connStartTime = System.currentTimeMillis();
        Connection con = DriverManager.getConnection(dbcs.getUrl(), dbcs.getUser(), dbcs.getPass());
        long connEndTime = System.currentTimeMillis();
        
        System.out.println("Got connection in "+(connEndTime-connStartTime)+"ms.");
        System.out.println("Database version:  "+con.getMetaData().getDatabaseProductVersion());
        System.out.println("Driver version:    "+con.getMetaData().getDriverVersion());
        
        String sql = "SELECT schema_version FROM def_param";
        
        System.out.println("\nExecuting query:   "+sql);
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        System.out.print("Result: ");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        
        rs.close();
        stmt.close();
        con.close();
        
        System.out.println("\nDone.  Test Successful!");
    }

    /**
     * Interactively reads a line of text from System.in.  A line is terminated
     * by CR, LF, or EOF.  Supports backspace, but no other fancy editing features.
     */
    private static String readLine() throws IOException {
        StringBuffer line = new StringBuffer();
        int ch;
        for (;;) {
            ch = System.in.read();
            if (ch == -1 || ch == '\r' || ch == '\n') break;
            if (ch == 0x7f || ch == 0x08) {
                if (line.length() > 0) {
                    // delete and backspace both work like backspace
                    line.deleteCharAt(line.length()-1);
                    System.out.print((char) 0x08);
                }
            } else {
                line.append((char) ch);
            }
        }
        System.out.println("");
        return line.toString();
    }

}
