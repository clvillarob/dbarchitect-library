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

import java.util.StringTokenizer;

/**
 * The URLLinker class is a filter that inserts HTML markup to make
 * any subtrings that are URLs clickable when viewed in a web browser.
 *
 * @version $Id$
 * @author Jonathan Fuerth
 */
public class URLLinker implements ColumnFilter {
	
	protected String extraAttributes;

	public URLLinker() {
		this("");
	}

	/**
	 * Creates a URLLinker which will insert the given string after
	 * the href attribute in the generated HTML a tags.  You can use
	 * it to make the links appear in a named window, or to set the
	 * anchor's CSS class, or anything else.
	 *
	 * @param extraAttributes A string that will be inserted after the
	 * href attribute in the generated HTML a tags.  Null is not
	 * allowed, but the empty string is ok.
	 */
	public URLLinker(String extraAttributes) {
		if(extraAttributes == null) {
			throw new NullPointerException();
		}
		this.extraAttributes = extraAttributes;
	}

	/**
	 * Makes HTML href anchors out of words in the input string which
	 * begin with http://, https://, ftp://, mailto:, gopher://, or
	 * telnet:.  A word is defined as a maximal string of consecutive
	 * characters which are not whitespace.  Whitespace characters are
	 * space, tab, linefeed, carriage return, and form feed (the
	 * default delimiter set for java.util.StringTokenizer).
	 */
	public String filter(String in) {
		StringBuffer out = new StringBuffer(in.length());
		StringTokenizer words = new StringTokenizer(in, " \t\n\r\f", true);
		while(words.hasMoreTokens()) {
			String curWord = words.nextToken();
			if(curWord.startsWith("http://")
			   || curWord.startsWith("https://")
			   || curWord.startsWith("ftp://")
			   || curWord.startsWith("mailto:")
			   || curWord.startsWith("gopher://")
			   || curWord.startsWith("telnet:")) {
				out.append("<a href=\"")
					.append(curWord).append("\" ").append(extraAttributes).append(">")
					.append(curWord).append("</a>");
			} else {
				out.append(curWord);
			}
		}
		return out.toString();
	}
}
