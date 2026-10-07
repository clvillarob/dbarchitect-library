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

import java.util.List;

/**
 * Defines a common way of getting a list of DBConnectionSpec objects
 * to offer the user of an application.  Implementations could use XML
 * files, properties files, RMI servers, JNDI servers, Oracle TNS name
 * interfaces, ODBC interfaces, or anything else to actually generate
 * the list.
 *
 * <p>The initial implementation is an XML file or RMI implementation
 * which is configured via a servlet which in turn gets configuration
 * data from the sysadmin through web.xml init prarmeters.
 *
 * @see ca.sqlpower.servlet.DBCSSourceServlet
 * @see ca.sqlpower.sql.XMLRMIDBCSSource
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public interface DBCSSource {

	/**
	 * A call to this method should generate a list of
	 * DBConnectionSpec objects using various implementation-specific
	 * means of creating this list as necessary.
	 *
	 * @return a List of 0 or more DBConnectionSpec objects, never
	 * <code>null</code>.
	 * @throws DatabaseListReadException if the
	 * implementation-specific list creation process could not be
	 * completed.
	 */
	public List getDBCSList() throws DatabaseListReadException;
}
