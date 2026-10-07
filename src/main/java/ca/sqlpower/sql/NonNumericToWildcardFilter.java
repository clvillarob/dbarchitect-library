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

public class NonNumericToWildcardFilter implements ColumnFilter {
    static final int STATE_NUMBERS=0;
    static final int STATE_NON_NUMBERS=1;
    char wildcard='%';
    boolean leadingWildcard=false;
    boolean trailingWildcard=false;

    public static ColumnFilter getInstance() {
		return (ColumnFilter)new NonNumericToWildcardFilter();
    }

    public void setWildcardCharacter(char wild) {
		wildcard=wild;
    }

    public void setForceLeadingWildcard(boolean enabled) {
		leadingWildcard=enabled;
    }

    public void setForceTrailingWildcard(boolean enabled) {
		trailingWildcard=enabled;
    }

    public String filter(String in) {
		char ch;
		int state=STATE_NUMBERS;
		StringBuffer out=new StringBuffer(in.length());
		
		for(int i=0; i<in.length(); i++) {
			ch=in.charAt(i);
			if(ch < '0' || ch > '9') {
				if(state==STATE_NUMBERS) {
					out.append(wildcard);
				}
				state=STATE_NON_NUMBERS;
			} else {
				out.append(ch);
				state=STATE_NUMBERS;
			}
		}
		
		if(out.charAt(0) != wildcard) {
			out.insert(0, wildcard);
		}
		
		if(out.charAt(out.length()-1) != wildcard) {
			out.append(wildcard);
		}
		
		return out.toString();
    }
}
