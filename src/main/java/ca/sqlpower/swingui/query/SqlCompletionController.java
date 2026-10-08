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

package ca.sqlpower.swingui.query;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/** Lightweight SQL autocompletion for an {@link RSyntaxTextArea}. */
public class SqlCompletionController {

    private static final int MAX_VISIBLE_ROWS = 10;

    private final RSyntaxTextArea textArea;
    private final SqlCompletionProvider provider;
    private final JPopupMenu popup = new JPopupMenu();
    private final DefaultListModel<String> model = new DefaultListModel<String>();
    private final JList<String> list = new JList<String>(model);

    public SqlCompletionController(RSyntaxTextArea textArea, SqlCompletionProvider provider) {
        this.textArea = textArea;
        this.provider = provider;
        list.setVisibleRowCount(MAX_VISIBLE_ROWS);
        popup.setFocusable(false);
        popup.add(new JScrollPane(list));
        textArea.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && e.isControlDown()) {
                    showCompletions();
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    popup.setVisible(false);
                } else if (popup.isVisible()
                        && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB)) {
                    insertSelection();
                    e.consume();
                }
            }
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetterOrDigit(c) || c == '_') {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() { showCompletions(); }
                    });
                } else {
                    popup.setVisible(false);
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { insertSelection(); }
        });
        textArea.addFocusListener(new FocusAdapter() {
            @Override public void focusLost(FocusEvent e) { popup.setVisible(false); }
        });
    }

    public void showCompletions() {
        String fullText = textArea.getText();
        int caret = textArea.getCaretPosition();
        String prefix = currentPrefix(fullText, caret);
        List<String> candidates = provider.getCompletions(prefix, fullText, caret);
        if (candidates == null || candidates.isEmpty()) {
            popup.setVisible(false);
            return;
        }
        model.clear();
        for (String c : candidates) model.addElement(c);
        list.setSelectedIndex(0);
        try {
            Rectangle2D r = textArea.modelToView2D(caret);
            popup.show(textArea, (int) r.getX(), (int) (r.getY() + r.getHeight()));
        } catch (BadLocationException ex) {
            popup.setVisible(false);
        }
    }

    public void insertSelection() {
        if (!popup.isVisible()) return;
        String sel = list.getSelectedValue();
        popup.setVisible(false);
        if (sel == null) return;
        String fullText = textArea.getText();
        int caret = textArea.getCaretPosition();
        String prefix = currentPrefix(fullText, caret);
        try {
            textArea.getDocument().remove(caret - prefix.length(), prefix.length());
            textArea.getDocument().insertString(caret - prefix.length(), sel, null);
        } catch (BadLocationException ex) {
            throw new IllegalStateException(ex);
        }
    }

    static String currentPrefix(String text, int caret) {
        int i = caret;
        while (i > 0) {
            char c = text.charAt(i - 1);
            if (Character.isLetterOrDigit(c) || c == '_') i--;
            else break;
        }
        return text.substring(i, caret);
    }
}
