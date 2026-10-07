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
package ca.sqlpower.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

/**
 * Utility class for dealing with Web Browsers.
 */
public class BrowserUtil {

    /** Launches the default browser to display a URL.
     * @throws IOException
     */
    public static void launch(String uri) throws IOException {
    	if (Desktop.isDesktopSupported()) {
    		Desktop.getDesktop().browse(URI.create(uri));
    	}
    }
}
