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

import java.util.Map;
import java.util.Date;

/**
 * The Cache interface extends the normal Java Map interface to
 * provide the methods and parameters necessary for generic object
 * caching.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public interface Cache<K, V> extends Map<K, V> {

	/**
	 * Sets the maximum member count, which influences the behaviour
	 * of <code>itemsInserted</code>.
	 */
	public void setMaxMembers(int argMaxMembers);

	/**
	 * Gets the maximum member count, which influences the behaviour
	 * of <code>itemsInserted</code>.
	 */
	public int getMaxMembers();

	/**
	 * Gets the last time this cache was fully emptied.  Useful for
	 * deciding if the cache needs to be refreshed.  The cache will
	 * set the flush date when it is first created, and again every
	 * time the flush() method is called.
	 */
	public Date getLastFlushDate();

	/**
	 * Removes all items from the cache, and records the current time
	 * as the last flush date.
	 */
	public void flush();
	
	/**
	 * Returns the instance of CacheStats which contains useful
	 * statistics for tuning this cache.
	 */
	public CacheStats getStats();

}
