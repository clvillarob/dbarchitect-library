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

package ca.sqlpower.sqlobject;

import ca.sqlpower.object.SPObject;

public class TestSQLCheckConstraint extends BaseSQLObjectTestCase {
	
	private SQLCheckConstraint checkConstraint;

	public TestSQLCheckConstraint(String name) throws Exception {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		checkConstraint = (SQLCheckConstraint) createNewValueMaker(
				getRootObject(), getPLIni()).makeNewValue(
						SQLCheckConstraint.class, null, "SQLCheckConstraint for test");
	}
	
	@Override
	protected SQLObject getSQLObjectUnderTest() throws SQLObjectException {
		return checkConstraint;
	}

	@Override
	protected Class<? extends SPObject> getChildClassType() {
		return null;
	}

}
