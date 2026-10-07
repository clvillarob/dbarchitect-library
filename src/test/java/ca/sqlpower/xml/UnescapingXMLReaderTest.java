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
package ca.sqlpower.xml;

import junit.framework.TestCase;

import org.xml.sax.helpers.DefaultHandler;

public class UnescapingXMLReaderTest extends TestCase {

    private UnescapingXMLReader reader;
    
    protected void setUp() throws Exception {
        super.setUp();
        reader = new UnescapingXMLReader(new StubReader());
    }

    public void testWrapContentHandler() {
        DefaultHandler defaultHandler = new DefaultHandler();
        reader.setContentHandler(defaultHandler);
        
        // no matter what we put in, we should get an escaping content handler back
        assertEquals(UnescapingDefaultHandler.class, reader.getContentHandler().getClass());
    }

}
