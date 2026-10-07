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


/**
 * YesNoFilter helps with displaying the SQLPower standard 'Y'/'N'
 * indicators with internationalization support.
 *
 * <p><i>I say 'yes', you say 'no' / I say 'stop' and you say 'go, go, go..'</i>
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class YesNoFilter implements ColumnFilter {
	
	/**
	 * The string that will be used when a 'Y' is filtered.
	 */
	private String yString;

	/**
	 * The string that will be used when an 'N' is filtered.
	 */
	private String nString;
	
	/**
	 * Creates a new YesNoFilter.
	 * 
	 * @param yesString The string that will be used when a 'Y' is filtered.
	 * @param noString The string that will be used when an 'N' is filtered.
	 */	 
	public YesNoFilter(String yesString, String noString) {
		yString=yesString;
		nString=noString;
	}

	/**
	 * Turns 'Y' into whatever you set yesString to, and 'N' into
	 * whatever you set noString to.  Passes everything else
	 * (including <code>null</code>) as-is.
	 *
	 * @param in The input string
	 * @return as specified above.
	 */
    public String filter(String in) {
    	if (in == null) {
    		return null;
    	} else if (in.equals("Y")) {
    		return yString;
    	} else if (in.equals("N")) {
			return nString;
		} else {
			return in;
		}
    }
}
