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

import java.sql.SQLException;

/**
 * The RowFilter interface specifies a method for accepting or rejecting
 * a row of data based on whatever criteria the implemented chooses.
 *
 * @version $Id:$
 */
public interface RowFilter {

    /**
     * Returns true if and only if this row of data meets the set of criteria
     * determined by the filter implementation.
     * 
     * @param row
     *            The row of data to evaluate
     * @return true if the row passes this filter; false if it is rejected by
     *         this filter.
     * @throws SQLException
     *             if there are any database errors encountered while processing
     *             the given row.
     */
    boolean acceptsRow(Object[] row) throws SQLException;
}
