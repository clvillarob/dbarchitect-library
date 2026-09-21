/*
 * Copyright (c) 2026, SQL Power Group Inc.
 *
 * This file is part of SQL Power Library.
 *
 * SQL Power Library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * SQL Power Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.sqlpower.util;

import junit.framework.TestCase;

/**
 * Tests that {@link Version} accepts the "V"/"v" release-tag prefix used by the
 * Power*Architect repository (e.g. V1.9.0), and that the prefix does not affect
 * numeric comparison.
 */
public class VersionTest extends TestCase {

    public void testParseWithVendorVPrefix() {
        Version v = new Version("V1.9.0");
        assertEquals(3, v.getParts().length);
        assertEquals(1, v.getParts()[0]);
        assertEquals(9, v.getParts()[1]);
        assertEquals(0, v.getParts()[2]);
        assertEquals("V1.9.0", v.toString());
    }

    public void testParseWithLowercaseVPrefix() {
        Version v = new Version("v2.0.11");
        assertEquals(3, v.getParts().length);
        assertEquals(2, v.getParts()[0]);
        assertEquals(0, v.getParts()[1]);
        assertEquals(11, v.getParts()[2]);
        assertEquals("V2.0.11", v.toString());
    }

    public void testCompareIgnoresVPrefix() {
        Version withVPrefix = new Version("V1.9.0");
        Version withoutVPrefix = new Version("1.9.0");
        assertEquals(0, withVPrefix.compareTo(withoutVPrefix));

        Version v2 = new Version("V2.0.11");
        assertTrue(v2.compareTo(withVPrefix) > 0);
    }

    public void testParseWithoutVPrefixStillWorks() {
        Version v = new Version("1.2.3");
        assertEquals(3, v.getParts().length);
        assertEquals("1.2.3", v.toString());
    }
}