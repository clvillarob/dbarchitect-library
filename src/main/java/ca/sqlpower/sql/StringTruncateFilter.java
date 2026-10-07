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
 * StringTruncateFilter truncates a string to a specific length.
 *
 * @author Jonathan Fuerth and Dan Fraser
 * @version $Id$
 */
public class StringTruncateFilter implements ColumnFilter {
	/**
	 * The length of the final string.
	 */
	int length;
	
	/**
	 * Creates a new StringTruncateFilter.
	 * 
	 * @param length the maximum length of the returned strings.
	 */	 
	public StringTruncateFilter(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("length must be nonnegative");
		}
		this.length = length;
	}

	/**
	 * Filters the input string based on the length set in this filter.
	 *
	 * @param in The input string
	 * @return the truncated string, or null if the input string was null.
	 */
    public String filter(String in) {
    	if (in == null) {
    		return null;
    	}
    	if (in.length() < length) {
    		return in;
    	}
		return in.substring(0,length);
    }
    
	/**
	 * Returns the length.
	 * @return int
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 * @param length The length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
