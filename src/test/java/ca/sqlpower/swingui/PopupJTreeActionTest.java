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

package ca.sqlpower.swingui;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import junit.framework.TestCase;

/**
 * Tests for the search feature of {@link PopupJTreeAction}. The search must
 * work with any {@link TreeModel}, including models whose root is not a
 * {@link javax.swing.tree.TreeNode} (like the Architect's SQLTypeTreeModel,
 * whose root is an ArchitectProject).
 */
public class PopupJTreeActionTest extends TestCase {

    private static final class SimpleStringTreeModel implements TreeModel {
        private final Object root = "Root"; //$NON-NLS-1$
        private final Object[] children = new Object[] { "ChildOne", "OtherChild" }; //$NON-NLS-1$ //$NON-NLS-2$

        public Object getRoot() {
            return root;
        }

        public Object getChild(Object parent, int index) {
            if (parent == root && index < children.length) {
                return children[index];
            }
            return null;
        }

        public int getChildCount(Object parent) {
            if (parent == root) {
                return children.length;
            }
            return 0;
        }

        public boolean isLeaf(Object node) {
            return getChildCount(node) == 0;
        }

        public int getIndexOfChild(Object parent, Object child) {
            if (parent == root) {
                for (int i = 0; i < children.length; i++) {
                    if (children[i] == child) {
                        return i;
                    }
                }
            }
            return -1;
        }

        public void addTreeModelListener(TreeModelListener l) {
            // no-op
        }

        public void removeTreeModelListener(TreeModelListener l) {
            // no-op
        }

        public void valueForPathChanged(TreePath path, Object newValue) {
            // no-op
        }
    }

    /**
     * The search must not assume the tree root is a {@link javax.swing.tree.TreeNode}.
     * This is the regression test for the ClassCastException thrown when searching
     * the SQL type tree (whose root is an ArchitectProject).
     */
    public void testFindMatchWorksWithNonTreeNodeRoot() {
        TreeModel model = new SimpleStringTreeModel();
        TreePath rootPath = new TreePath(model.getRoot());

        TreePath match = PopupJTreeAction.findMatch(
                model, model.getRoot(), rootPath, "child"); //$NON-NLS-1$

        assertNotNull("Search should find a match for 'child'", match);
        assertEquals("ChildOne", match.getLastPathComponent());
    }

    public void testFindMatchReturnsNullWhenNoMatch() {
        TreeModel model = new SimpleStringTreeModel();
        TreePath rootPath = new TreePath(model.getRoot());

        TreePath match = PopupJTreeAction.findMatch(
                model, model.getRoot(), rootPath, "zzz"); //$NON-NLS-1$

        assertNull(match);
    }
}