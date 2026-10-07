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
package ca.sqlpower.object;

import ca.sqlpower.object.SPObject;


/**
 * Each edge is made up of a parent {@link WabitObject} and a child
 * {@link WabitObject}. The edge goes in the direction from the parent to
 * the child.
 */
public class WorkspaceGraphModelEdge {
    
    private final SPObject parent;
    private final SPObject child;

    public WorkspaceGraphModelEdge(SPObject parent, SPObject child) {
    	// See ticket #2660 if issues with edge being formed between parent and a null child (out of the blues..?) arise.   
    	if (child == null) throw new IllegalArgumentException("Cannot form an edge between parent [" + parent.toString() + "] and null");
        
    	this.parent = parent;
        this.child = child;
    }
    
    public SPObject getParent() {
        return parent;
    }
    
    public SPObject getChild() {
        return child;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkspaceGraphModelEdge) {
            WorkspaceGraphModelEdge wabitObject = (WorkspaceGraphModelEdge) obj;
            return getParent().equals(wabitObject.getParent()) && getChild().equals(wabitObject.getChild());
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + getParent().hashCode();
        result = 37 * result + getChild().hashCode();
        return result;
    }
}