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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;


/**
 * Remote interface used to obtain the list of database connections
 * from a central server (for clustering this web application).
 *
 * @author Dan Fraser
 * @version $Id$
 */
public interface DBConnectionSpecServer extends Remote {

	/**
	 * Returns a collection of DBConnectionSpec objects.
	 */
	public Collection getAvailableDatabases() throws RemoteException;

	/** 
	 * Returns true if the password in the argument matches the administrative
	 * password on the RMI server.  Returns false otherwise.
	 */
	public boolean checkPassword(String password) throws RemoteException;
	
	/**
	 * This sets the list of available databases on the RMI server.
	 * 
	 * @param dbList a Collection of DBConnectionSpec objects.
	 * @param oldPass the current administrative password (required)
	 * @param newPass if non-null and non-empty, the password will
	 * be changed to this password.
	 */
	public void setAvailableDatabases(Collection dbList, String oldPass, String newPass) 
		throws RemoteException;

}

