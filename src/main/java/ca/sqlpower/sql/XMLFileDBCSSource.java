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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A plain XML-file-based source for the list of DBConnectionSpec
 * objects that should be presented to the user when they need to pick
 * a database to connect to.
 * <p>
 * Each instance of this class keeps a cached copy of the connection info
 * list, and will automatically reload the list from the file if its 
 * modification time changes. 
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class XMLFileDBCSSource implements DBCSSource, Serializable {

	private String xmlFileName;
    
    /**
     * A cached copy of the last DBCS List we returned.  Profiling shows that
     * parsing the XML file every time we get to the login screen is quite
     * wasteful of memory.
     */
    private List cachedDbcsList;

    /**
     * The time (from System.currentTimeMillis()) that the cachedDbcsList was
     * last read from the file.  We compare this with the file's modification
     * time to see if it needs to be reloaded.
     */
    private long cachedDbcsListRefreshTime;
    
	public XMLFileDBCSSource(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	/**
	 * Returns a list of DBConnectionSpec objects which were retrieved
	 * based on configuration information given to the constructor.
	 *
	 * @throws DatabaseListReadException if there is a problem reading
	 * the database list from the XML file.
	 */
	public synchronized List getDBCSList() throws IllegalStateException, DatabaseListReadException {
	    File dbcsFile = new File(xmlFileName);
	    if (cachedDbcsList == null || dbcsFile.lastModified() >= cachedDbcsListRefreshTime) {
	        InputStream dbXMLFile = null;
	        
	        try {
	            dbXMLFile = new BufferedInputStream(new FileInputStream(dbcsFile));
	            cachedDbcsList = new ArrayList(DBCSSourceSupport.getListUsingXMLStream(dbXMLFile));
	            cachedDbcsListRefreshTime = System.currentTimeMillis();
	        } catch (IOException e) {
	            throw new DatabaseListReadException(e);
	        } finally {
	            try {
	                if (dbXMLFile != null) dbXMLFile.close();
	            } catch(IOException e) {
	                throw new DatabaseListReadException(e);
	            }
	        }
	    }
	    return cachedDbcsList;
	}
}
