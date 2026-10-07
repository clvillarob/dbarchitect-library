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

/**
 * The TableTextConverter interface is a way for users of a table
 * to find out what text will be displayed on the screen for a particular
 * cell.  This interface can be implemented on a JTable subclass, where
 * implementers should have special knowledge of how values are rendered.
 * For example, if all the cell renderers for your table are JLabels, then
 * you can get the renderer component and cast it to JLabel and get its text.
 */
public interface TableTextConverter {
    
    /**
     * Returns the canonical String value associated with the given
     * row and column of a table.
     */
    String getTextForCell(Object value);
}
