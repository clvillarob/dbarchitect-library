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
package ca.sqlpower.swingui.table;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.table.DefaultTableModel;

import junit.framework.TestCase;

public class TableModelJSONFormatterTest extends TestCase {

    public void testFormatsRowsToJsonArray() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] { { Integer.valueOf(1), "Alice" }, { Integer.valueOf(2), "Bob" } },
                new Object[] { "id", "name" });
        StringWriter sw = new StringWriter();
        new TableModelJSONFormatter().formatToStream(model, new PrintWriter(sw));
        String json = sw.toString();
        assertTrue("numbers are unquoted: " + json, json.contains("\"id\": 1"));
        assertTrue("strings are quoted: " + json, json.contains("\"name\": \"Alice\""));
        assertTrue(json.contains("\"name\": \"Bob\""));
        assertTrue(json.trim().startsWith("["));
        assertTrue(json.trim().endsWith("]"));
    }

    public void testNullBecomesJsonNull() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] { { null } }, new Object[] { "v" });
        StringWriter sw = new StringWriter();
        new TableModelJSONFormatter().formatToStream(model, new PrintWriter(sw));
        assertTrue("null must become JSON null: " + sw.toString(), sw.toString().contains("\"v\": null"));
    }

    public void testSelectedRowsOnly() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] { { "a" }, { "b" }, { "c" } }, new Object[] { "v" });
        StringWriter sw = new StringWriter();
        new TableModelJSONFormatter().formatToStream(model, new PrintWriter(sw), new int[] { 1 });
        assertTrue(sw.toString().contains("\"b\""));
        assertFalse(sw.toString().contains("\"a\""));
        assertFalse(sw.toString().contains("\"c\""));
    }

    public void testEscapeQuotesAndBackslashes() {
        assertEquals("a\\\"b", TableModelJSONFormatter.escape("a\"b"));
        assertEquals("a\\\\b", TableModelJSONFormatter.escape("a\\b"));
        assertEquals("a\\nb", TableModelJSONFormatter.escape("a\nb"));
    }
}
