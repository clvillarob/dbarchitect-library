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

public class Passwords {
    private Passwords() {
    }

    public static final int QUALITY_LENIENT=1;
    public static final int QUALITY_STRICT=9;

    /**
     * 
     */
    public static boolean checkQuality(String pw, int severity) {

	// Long enough?
	if(pw.length() < 6) {
	    return false;
	}

	boolean hasUpperCase=false;
	boolean hasLowerCase=false;
	boolean hasDigits=false;
	boolean hasPunctuation=false;

	for(int i=0; i<pw.length(); i++) {
	    char ch=pw.charAt(i);
	    if( (ch < 32) && (ch > 126) ) {
		return false;
	    } else if(('A' <= ch) && (ch <= 'Z')) {
		hasUpperCase=true;
	    } else if(('a' <= ch) && (ch <= 'z')) {
		hasLowerCase=true;
	    } else if(('0' <= ch) && (ch <= '9')) {
		hasDigits=true;
	    } else {
		hasPunctuation=true;
	    }
	}

	// If it's long enough and contains no nasties, that's enough
	// for a lenient check.
	if(severity <= QUALITY_LENIENT) {
	    return true;
	}

	// Otherwise, we get picky...
	if(hasUpperCase && hasLowerCase && (hasDigits || hasPunctuation)) {
	    return true;
	}

	return false;
    }
}
