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

import java.rmi.Naming;
import java.util.Collection;
import java.util.Iterator;
/**
 * Test stub to allow basic testing of the RMI server.
 * 
 * @author Dan Fraser
 */
public class RmiTest {

	public static void main(String[] args) {
		
		
    Collection message = null; 
         
    DBConnectionSpecServer obj = null; 

        try { 
            obj = (DBConnectionSpecServer)Naming.lookup("//arthur/DBConnectionSpecServer"); 
            message = obj.getAvailableDatabases();
            Iterator messageIt = message.iterator();
            while (messageIt.hasNext()) {
            	System.out.println(messageIt.next());
            }
            System.out.println("passcheck: "+obj.checkPassword("mo"));
            obj.setAvailableDatabases(message,"cow","cow");
        } catch (Exception e) { 
            System.out.println("exception: " + e.getMessage()); 
            e.printStackTrace(); 
        } 
    } 
}

