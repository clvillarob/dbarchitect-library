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

import java.io.*;

class ZealousURLEncoderTest {
    public static void main(String[] args) throws Exception {
	String first;
	try {
	    first=args[0];
	} catch (Exception e) {
	    first="This is a stock test string.  You can supply your own on the command-line!";
	}
	String second=ca.sqlpower.util.ZealousURLEncoder.zealousEncode(first);
	String third=java.net.URLDecoder.decode(second);

	System.out.println("       Your original string: "+first);
	System.out.println("       Your string, encoded: "+second);
	System.out.println("The encoded, decoded string: "+third);
    }
}
