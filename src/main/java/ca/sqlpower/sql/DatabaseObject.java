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
 * Represents the common fields between all SQL*Power database
 * records.  Currently, this is just the Object name and its object
 * type (the string used to identify the implementing object's type in
 * the security tables and elsewhere in the database).  The
 * ca.sqlpower.dashboard.Kpi class will be the first to implement this
 * interface.
 *
 * <p>Note that this interface extends Serializable, so you must
 * ensure that your implementing classes are indeed serializable.
 *
 * @version $Id$
 */
public interface DatabaseObject extends java.io.Serializable {

	/**
	 * Returns the object's name, suitable for use in SQL WHERE clauses.
	 */
	public String getObjectName();

	/**
	 * Returns the object's type, which corresponds with the strings
	 * used to identify object types in the security tables.
	 */
	public String getObjectType();
}
