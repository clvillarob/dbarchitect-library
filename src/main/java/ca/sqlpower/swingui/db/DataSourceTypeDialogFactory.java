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
package ca.sqlpower.swingui.db;

import java.awt.Window;


/**
 * Generic interface for creating and showing a dialog box that allows users to
 * edit a database connection type (SPDatasourceType) object.
 * <p>
 * Different applications have different extra fields they want the user to fill
 * out, so the library can't just create a dialog box on its own for doing this.
 */
public interface DataSourceTypeDialogFactory {

	/**
	 * Shows the user interface for maintaining the data source types.
	 * If the interface is already visible it is brought to the front and
	 * given focus.
	 * 
	 * @param owner The dialog or frame that owns the dialog that this factory creates 
	 * @return The dialog that has been shown
	 */
	public Window showDialog(Window owner);
}
