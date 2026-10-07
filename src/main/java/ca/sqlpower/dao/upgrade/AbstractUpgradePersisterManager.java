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

package ca.sqlpower.dao.upgrade;

import java.util.ArrayList;

import ca.sqlpower.dao.SPUpgradePersister;

public abstract class AbstractUpgradePersisterManager implements
		UpgradePersisterManager {

	private ArrayList<SPUpgradePersister> upgradePersisters = new ArrayList<SPUpgradePersister>();
	
	/**
     * Registers an upgrade persister that can take persist calls from the
     * repository of the form of the old version and convert them to persist
     * calls of the repository at the next version. In order for the upgrade
     * persister to be properly registered there must be an upgrade persister at
     * the previous version or the old version must be the 0th version.
     * 
     * @param oldVersion
     *            The version to upgrade persist calls from. 
     * @param upgradePersister
     *            An upgrade persister that can convert persist calls from a
     *            server at repository version oldVersion to the repository
     *            version of oldVersion + 1.
     * @throws IllegalArgumentException
     *             If the oldVersion is not equal to 0 or there is no persister
     *             currently registered at oldVersion -1 or if a persister is
     *             already registered for this version.
     */
	protected void registerUpgradePersister(int oldVersion, SPUpgradePersister upgradePersister) {
		if (oldVersion < upgradePersisters.size()) throw new IllegalArgumentException(
	            "A persister is already registered to upgrade repositories from version " + oldVersion);
	    if (oldVersion == 0) {
	        upgradePersisters.add(oldVersion, upgradePersister);
	    } else if (oldVersion == upgradePersisters.size()) {
	        upgradePersisters.add(oldVersion, upgradePersister);
	        upgradePersisters.get(oldVersion - 1).setNextPersister(upgradePersister, true);
	    } else {
	        throw new IllegalArgumentException("There is no persister at revision " + 
	                (oldVersion - 1) + " to chain this persister to.");
	    }
	}
	
	@Override
	public SPUpgradePersister getUpgradePersister(int version) {
		if (version >= getStateVersion()) {
			return null;
		} else {
			return upgradePersisters.get(version);
		}
	}

}
