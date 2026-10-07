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

package ca.sqlpower.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;

import ca.sqlpower.dao.SPPersister.DataType;
import ca.sqlpower.dao.upgrade.UpgradePersisterManager;

public class XMLPersisterTest extends PersisterTest {

	ByteArrayOutputStream out = new ByteArrayOutputStream();
	private UpgradePersisterManager upgradePersisterManager;
	
	public void setUp() throws Exception {
		super.setUp();
		upgradePersisterManager = new UpgradePersisterManager() {
			@Override
			public int getStateVersion() {
				return 0;
			}
			
			@Override
			public SPUpgradePersister getUpgradePersister(int version) {
				return null;
			}
		};
		XMLPersister.setUpgradePersisterManager(upgradePersisterManager);
		persister = new XMLPersister(out, "ca.sqlpower.testutil.SPObjectRoot", "tester");
		persister.begin();
		persister.persistObject(null, "ca.sqlpower.testutil.SPObjectRoot", workspaceId, 0);
		persister.persistProperty(workspaceId, "name", DataType.STRING, "rtObjName");
	}
	
	public void testPersistNull() {
		// XMLPersister can't change values, so this test doesn't apply
	}
	
	public void testConditionalPersistProperty() {
		// XMLPersister can't change values, so this test doesn't apply
	}
	
	public void testRemoveObject() {
		// XMLPersister can't change values, so this test doesn't apply
	}
	
	public void testRollback() {
		// XMLPersister can't change values, so this test doesn't apply
		persister.rollback();
		assertEquals("", out.toString());
	}
	
	@Override
	protected void loadWorkspace() throws Exception {
		persister.commit();
		XMLPersisterReader reader = new XMLPersisterReader(new InputStreamReader(new ByteArrayInputStream(out.toByteArray())), receiver, upgradePersisterManager, "tester");
		reader.read();
	}
	
}
