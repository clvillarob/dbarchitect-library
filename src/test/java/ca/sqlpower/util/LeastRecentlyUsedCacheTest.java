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

import junit.framework.*;
import ca.sqlpower.util.*;

public class LeastRecentlyUsedCacheTest extends CacheTest {

	public void setUp() {
		cache = new LeastRecentlyUsedCache(maxMembers);
		super.setUp();
	}

	public void testLRUPolicy() {
		String val = null;

		// request all items starting with 44
		for (int i = 44; i >= 0; i--) {
			val = (String) cache.get(new Integer(i));
			assertEquals(val, String.valueOf(i));
		}

		// insert 6 new items
		for (int i = 45; i < 51; i++) {
			cache.put(new Integer(i), String.valueOf(i));
		}
		
		// least recently used item should be gone
		assertNull(cache.get(new Integer(44)));
		assertEquals(cache.get(new Integer(43)), String.valueOf(43));

		// insert one more item and re-check (42 will be LRU because we just used 43)
		cache.put(new Integer(52), String.valueOf(52));
		assertNull(cache.get(new Integer(42)));
	}
}
