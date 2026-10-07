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

/**
 * The Hyperlink class represents a link with text and a hypertext
 * reference.  It does not use the java.net.URL class internally,
 * because it is intended that the href attribute will often be
 * formatted using the LongMessageFormat class.
 */
public class Hyperlink {
	protected String text;
	protected String href;

	public Hyperlink(String text, String href) {
		this.text=text;
		this.href=href;
	}

	
	/**
	 * Gets the value of text
	 *
	 * @return the value of text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the value of text
	 *
	 * @param argText Value to assign to this.text
	 */
	public void setText(String argText){
		this.text = argText;
	}

	/**
	 * Gets the value of href
	 *
	 * @return the value of href
	 */
	public String getHref() {
		return this.href;
	}

	/**
	 * Sets the value of href
	 *
	 * @param argHref Value to assign to this.href
	 */
	public void setHref(String argHref){
		this.href = argHref;
	}

}
