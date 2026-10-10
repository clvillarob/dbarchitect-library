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
import java.text.Format;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableModel;

/**
 * Exports a {@link TableModel} to a JSON array of objects: one object per row,
 * keyed by column name. {@code null} cells become JSON {@code null}; numeric and
 * boolean values are emitted unquoted (unless overridden by a column formatter,
 * which is always treated as a string).
 */
public class TableModelJSONFormatter implements ExportFormatter {

    /**
     * Formatters are given an object from the table model and must output a
     * string representation of that object (see {@link TableModelCSVFormatter}).
     */
    private final Map<Integer, Format> columnFormatters = new HashMap<Integer, Format>();

    /**
     * Sets a formatter for the given column of a table model. If the column does
     * not exist because the table is too small the formatter will not be used.
     */
    public void setFormatter(int column, Format formatter) {
        columnFormatters.put(column, formatter);
    }

    /**
     * Converts the given model into a JSON array and writes it to the output
     * stream.
     */
    public void formatToStream(TableModel model, PrintWriter writer) {
        int[] allRows = new int[model.getRowCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            allRows[i] = i;
        }
        formatToStream(model, writer, allRows);
    }

    /**
     * Outputs a JSON array that represents the TableModel. Only the rows given
     * will be written to the file.
     */
    public void formatToStream(TableModel model, PrintWriter writer, int[] selectedRows) {
        int columnCount = model.getColumnCount();
        writer.println("[");
        for (int i = 0; i < selectedRows.length; i++) {
            writer.print("  {");
            for (int col = 0; col < columnCount; col++) {
                if (col > 0) {
                    writer.print(", ");
                }
                writer.print("\"" + escape(model.getColumnName(col)) + "\": ");
                Object value = model.getValueAt(selectedRows[i], col);
                if (value == null) {
                    writer.print("null");
                } else if (columnFormatters.get(col) == null
                        && (value instanceof Number || value instanceof Boolean)) {
                    writer.print(value.toString());
                } else {
                    Object rendered = columnFormatters.get(col) != null
                            ? columnFormatters.get(col).format(value) : value.toString();
                    writer.print("\"" + escape(rendered.toString()) + "\"");
                }
            }
            writer.print("}");
            if (i < selectedRows.length - 1) {
                writer.print(",");
            }
            writer.println();
        }
        writer.println("]");
        writer.flush();
    }

    /**
     * Escapes a string for inclusion inside a JSON string literal.
     */
    static String escape(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            default:
                if (c < 0x20) {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
