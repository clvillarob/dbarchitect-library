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

package ca.sqlpower.swingui.action;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ca.sqlpower.swingui.SPSUtils;
import ca.sqlpower.util.BrowserUtil;

/**
 * This action will open a browser to the SQL forums.
 */
public class ForumAction extends AbstractAction {
	
	public ForumAction(ImageIcon buttonIcon, String description) {
		super("", buttonIcon);
		putValue(SHORT_DESCRIPTION, description);		
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			BrowserUtil.launch(SPSUtils.FORUM_URL);
		} catch (IOException e1) {
			throw new RuntimeException("Unexpected error in launch", e1); //$NON-NLS-1$
		}
	}

}
