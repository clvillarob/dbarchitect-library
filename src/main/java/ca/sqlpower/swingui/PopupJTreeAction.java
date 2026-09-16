/*
 * Copyright (c) 2010, SQL Power Group Inc.
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

package ca.sqlpower.swingui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.Popup;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * This {@link Action} creates a {@link Popup} with a {@link JTree} embedded
 * inside of it.
 */
public class PopupJTreeAction extends AbstractAction {

	/**
	 * The {@link JPanel} the generated {@link Popup} should appear on.
	 */
    private final JPanel panel;

	/**
	 * The {@link JTree} that should be embedded inside the generated
	 * {@link Popup}.
	 */
    private final JTree tree;

	/**
	 * The {@link JButton} that performs this {@link Action} to produce the
	 * {@link Popup}.
	 */
    private final JButton button;

	/**
	 * The {@link List} of valid {@link Class} types that a selected
	 * {@link TreeNode} can be.
	 */
    private final List<Class<?>> validSelectionClasses;
    
    /**
     * The {@link PopupListenerHandler}
     */
    private PopupListenerHandler popupListenerHandler;

    /**
     * When true, the popup shows a search field above the tree that scrolls to
     * (and selects) the first node whose label contains the typed text.
     */
    private boolean withSearch;

    /**
     * The search field, when {@link #withSearch} is enabled.
     */
    private JTextField searchField;

	/**
	 * This {@link TreeSelectionListener} determines if a tree selection is
	 * valid. If it is valid, the popup is cleaned up.
	 */
    private final TreeSelectionListener treeListener = new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getNewLeadSelectionPath();
            if (path != null) {
                Object node = path.getLastPathComponent();
                for (Class<?> c : validSelectionClasses) {
                	if (c.isAssignableFrom(node.getClass())) {
                		popupCleanup();
                		break;
                	}
                }
            }
        }
    };

	/**
	 * Creates a new {@link PopupJTreeAction} given the {@link JPanel} the
	 * created {@link Popup} should appear on, the {@link JTree} the
	 * {@link Popup} should embed, and the {@link JButton} that performs this
	 * action.
	 * 
	 * @param panel
	 *            The {@link JPanel} this {@link Popup} should appear on.
	 * @param tree
	 *            The {@link JTree} the {@link Popup} should embed.
	 * @param button
	 *            The {@link JButton} that should perform this action.
	 * @param validSelectionClass
	 *            The only one valid tree node type that can be selected. If
	 *            this is null, then no selections are allowed.
	 */
    public PopupJTreeAction(JPanel panel, JTree tree, JButton button, Class<?> validSelectionClass) {
    	this(panel, 
    			tree, 
    			button, 
    			(validSelectionClass == null)? 
    					Collections.<Class<?>>emptyList() : 
    						Collections.<Class<?>>singletonList(validSelectionClass));
    }

	/**
	 * Creates a new {@link PopupJTreeAction} given the {@link JPanel} the
	 * created {@link Popup} should appear on, the {@link JTree} the
	 * {@link Popup} should embed, and the {@link JButton} that performs this
	 * action.
	 * 
	 * @param panel
	 *            The {@link JPanel} this {@link Popup} should appear on.
	 * @param tree
	 *            The {@link JTree} the {@link Popup} should embed.
	 * @param button
	 *            The {@link JButton} that should perform this action.
	 * @param validSelectionClasses
	 *            The {@link List} of {@link Class}es that are valid tree
	 *            selections. A unmodifiable copy of this {@link List} is made
	 *            to use when validating the selection. If this is an empty
	 *            {@link List}, no selections are allowed.
	 */
    public PopupJTreeAction(JPanel panel, JTree tree, JButton button, List<Class<?>> validSelectionClasses) {
        this(panel, tree, button, validSelectionClasses, false);
    }

	/**
	 * Creates a new {@link PopupJTreeAction} with a single valid selection
	 * class, optionally with a search field.
	 */
    public PopupJTreeAction(JPanel panel, JTree tree, JButton button,
            Class<?> validSelectionClass, boolean withSearch) {
        this(panel, tree, button,
                (validSelectionClass == null)
                        ? Collections.<Class<?>>emptyList()
                        : Collections.<Class<?>>singletonList(validSelectionClass),
                withSearch);
    }

	/**
	 * Creates a new {@link PopupJTreeAction} as
	 * {@link #PopupJTreeAction(JPanel, JTree, JButton, List)}, optionally with a
	 * search field above the tree.
	 *
	 * @param withSearch
	 *            if true, the popup embeds a search field that scrolls to and
	 *            selects the first node whose label contains the typed text.
	 */
    public PopupJTreeAction(JPanel panel, JTree tree, JButton button,
            List<Class<?>> validSelectionClasses, boolean withSearch) {
        super();
        this.panel = panel;
        this.tree = tree;
        this.button = button;
        this.validSelectionClasses = Collections.unmodifiableList(new ArrayList<Class<?>>(validSelectionClasses));
        this.withSearch = withSearch;
    }

    /**
     * Creates a {@link Popup} if it is not visible, and connects the
     * appropriate listeners to check for when to hide the {@link Popup}.
     * Otherwise, it hides the {@link Popup} and disconnects the listeners.
     */
    public void actionPerformed(ActionEvent e) {
        if (popupListenerHandler != null && popupListenerHandler.isPopupVisible()) {
            popupCleanup();
        } else {
            Point windowLocation = new Point(0, 0);
            SwingUtilities.convertPointToScreen(windowLocation, button);
            windowLocation.y += button.getHeight();
           // tree.setMinimumSize(new Dimension(panel.getWidth(), tree.getHeight()));
            // Popup the JTree and attach the popup listener handler to the tree
            JComponent popupContent = withSearch ? buildSearchableContent() : tree;
            popupListenerHandler = 
                SPSUtils.popupComponent(panel, popupContent, windowLocation,true);
            if (searchField != null) {
                searchField.requestFocusInWindow();
            }
            popupConnect();
        }
    }

    /**
     * Builds a panel with a search field above the tree. Typing scrolls to and
     * selects the first node whose label contains the text.
     */
    private JPanel buildSearchableContent() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 2));
        searchField = new JTextField();
        wrapper.add(searchField, BorderLayout.NORTH);
        wrapper.add(new JScrollPane(tree), BorderLayout.CENTER);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchTree(); }
            public void removeUpdate(DocumentEvent e) { searchTree(); }
            public void changedUpdate(DocumentEvent e) { searchTree(); }
        });
        return wrapper;
    }

    private void searchTree() {
        if (searchField == null) return;
        String needle = searchField.getText();
        if (needle == null || needle.trim().isEmpty()) return;
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        TreePath match = findMatch(root, new TreePath(root), needle.trim().toLowerCase());
        if (match != null) {
            tree.setSelectionPath(match);
            tree.scrollPathToVisible(match);
        }
    }

    private TreePath findMatch(TreeNode node, TreePath path, String needle) {
        if (node.toString().toLowerCase().contains(needle)) {
            return path;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            TreeNode child = node.getChildAt(i);
            TreePath match = findMatch(child, path.pathByAddingChild(child), needle);
            if (match != null) return match;
        }
        return null;
    }

	/**
	 * Displays the {@link Popup} and attaches a {@link FocusListener} and
	 * {@link TreeSelectionListener} on the tree that is embedded.
	 */
    private void popupConnect() {
        if (popupListenerHandler != null) {
            popupListenerHandler.connect();
        }
        if (tree != null) {
            tree.addTreeSelectionListener(treeListener);
        }
    }

	/**
	 * Hides the {@link Popup} and removes all of the
	 * {@link TreeSelectionListener}s and {@link FocusListener}s on all of the
	 * related components that were hooked up using {@link #popupConnect()}.
	 */
    private void popupCleanup() {
        if (popupListenerHandler != null && popupListenerHandler.isPopupVisible()) {
            popupListenerHandler.cleanup();
        }
        if (tree != null) {
            tree.removeTreeSelectionListener(treeListener);
        }
    }

}
