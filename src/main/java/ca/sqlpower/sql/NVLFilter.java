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
 * NVLFilter simulates a SQL NVL() function on a WebResultSet column.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class NVLFilter implements ColumnFilter {

	String nullValue;
	
	/**
	 * Constructs a new NVLFilter which substitutes
	 * <code>nullValue</code> for nulls.
	 *
	 * @param nullValue The value to substitute for nulls.
	 */	 
	public NVLFilter(String nullValue) {
		this.nullValue=nullValue;
	}

	/**
	 * Makes a new NVLFilter which substitutes "?" for nulls.
	 *
	 * @return The new NVLFilter.
	 */
    public static ColumnFilter getInstance() {
        return (ColumnFilter)new NVLFilter("?");
    }

	/**
	 * Converts null values to a pre-determined string, returning
	 * non-null values unchanged.
	 *
	 * @param in The input string
	 * @return <code>in</code> if <code>in != null</code>; the value
	 * of the <code>nullValue</code> property otherwise.
	 */
    public String filter(String in) {
		if(in==null) {
			return nullValue;
		} else {
			return in;
		}
    }

	/**
	 * Gets the value of nullValue
	 *
	 * @return the value of nullValue
	 */
	public String getNullValue() {
		return this.nullValue;
	}

	/**
	 * Sets the value of nullValue
	 *
	 * @param argNullValue Value to assign to this.nullValue
	 */
	public void setNullValue(String argNullValue){
		this.nullValue = argNullValue;
	}
}
