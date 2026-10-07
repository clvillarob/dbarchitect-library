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

import java.util.Comparator;
import java.io.Serializable;

/**
 * A comparator which can be used to compare the first elements in an Object
 * array, as long as they are of type String.  This is normally handled in 
 * a helper class, but it's also needed by an applet, and it would have been 
 * wrong to pollute the applet namespace with a member from a servlet package
 * (where this comparator is being used).
 * 
 * Hence, we create it externally, and throw it into utils.  Maybe someone, 
 * somewhere will eventually reuse this, but I somehow doubt it :)
 *
 * @author Jack Cooney
 * @version $Id$
 */
public class StringArrayFirstElementComparator implements Comparator, Serializable {
	
	public StringArrayFirstElementComparator() {}

	public int compare(Object o1, Object o2) {
		String [] s1 = (String []) o1;
		String [] s2 = (String []) o2;
		return s1[0].compareTo(s2[0]);
	}
}
