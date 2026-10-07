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
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 * A source for the list of DBConnectionSpec objects that should be
 * presented to the user when they need to pick a database to connect
 * to.  Can retrieve the list from a local XML file (using the servlet
 * API's getResourceAsStream) or by an RMI call.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class XMLRMIDBCSSource implements DBCSSource, Serializable {

	protected ServletContext servletContext=null;
	protected String rmiServerName=null;
	protected String xmlResourcePath=null;

	public XMLRMIDBCSSource(ServletContext servletContext,
							String rmiServerName,
							String xmlResourcePath) {
		this.servletContext=servletContext;
		this.rmiServerName=rmiServerName;
		this.xmlResourcePath=xmlResourcePath;
	}

	/**
	 * Returns a list of DBConnectionSpec objects which were retrieved
	 * based on configuration information given to the constructor.
	 *
	 * @throws DatabaseListReadException if there is a problem reading
	 * the database list from either the RMI server or the XML file.
	 */
	public List getDBCSList()
		throws IllegalStateException, DatabaseListReadException {

		List dbcsList=null;

		if (rmiServerName != null) {
			dbcsList=new ArrayList(DBCSSourceSupport.getListUsingRMI(rmiServerName));
				//session.setAttribute(LoginAction.RMI_SERVER, rmiServer);
		} else if (xmlResourcePath != null) {
			InputStream dbXMLFile = servletContext.getResourceAsStream(xmlResourcePath);
            try {
				dbcsList=new ArrayList(DBCSSourceSupport.getListUsingXMLStream(dbXMLFile));
			} finally {
				try {
					dbXMLFile.close();
				} catch(IOException e) {
					throw new DatabaseListReadException(e);
				}
			}
        } else {
			throw new IllegalStateException("At least one of rmiServerName or "
											+"xmlResourcePath must have been specified.");
		}
		return dbcsList;
	}

	public boolean isUsingRmi() {
		return rmiServerName != null;
	}

	public String getRmiServerHostname() {
		return rmiServerName;
	}
}
