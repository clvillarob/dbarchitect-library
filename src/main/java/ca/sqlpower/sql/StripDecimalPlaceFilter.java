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
 * StripDecimalPlaceFilter removes everything after the first '.' in
 * the given string.  If the input string is null, "0" is returned.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class StripDecimalPlaceFilter implements ColumnFilter {

	/**
	 * Constructs a new StripDecimalPlaceFilter,
	 *
	 */	 
	public StripDecimalPlaceFilter() {
	}

	/**
	 * Effectively the same thing as <code>new StripDecimalPlaceFilter</code>.
	 */
    public static ColumnFilter getInstance() {
        return (ColumnFilter)new StripDecimalPlaceFilter();
    }

	/**
	 * Removes everything after the first '.' in the given string.
	 * If the input string is null, "0" is returned.
	 *
	 * @param in The input string
	 * @return All characters leading up to the first "." in the input
	 * string, or the entire string if there is no ".".  If <code>in
	 * == null</code>, "0" is returned.
	 */
    public String filter(String in) {
		if(in==null) {
			return "0";
		} else {
			int firstDot=in.indexOf('.');
			if(firstDot>=0) {
				return in.substring(0, firstDot);
			} else {
				return in;
			}
		}
    }
}
