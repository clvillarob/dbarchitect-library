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


public class PLSchemaException extends Exception {

	String currentVersion;
	String requiredVersion;
	
	public PLSchemaException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new PLSchemaException storing the required version and current version of the PL schema
	 * for better error message display as it bubbles up through the app.
	 * 
	 * @param currentVersion the version of the currently installed PL schema
	 * @param requiredVersion the minimum version of the PL Schema required to run this app.
	 */
	public PLSchemaException(String message, String currentVersion, String requiredVersion) {
		super(message);
		this.currentVersion = currentVersion;
		this.requiredVersion = requiredVersion;
	}
	
	/**
	 * Returns the currentVersion.
	 * @return String
	 */
	public String getCurrentVersion() {
		return currentVersion;
	}

	/**
	 * Returns the requiredVersion.
	 * @return String
	 */
	public String getRequiredVersion() {
		return requiredVersion;
	}

}
