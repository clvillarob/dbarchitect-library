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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.Icon;

/**
 * The AddRemoveIcon is a simple icon that draws a thick plus or minus sign,
 * usually for little "Add" and "Remove" buttons under a list component.
 */
public class AddRemoveIcon implements Icon {

    public static enum Type {
        ADD, REMOVE;
    }
    
    /**
     * This icon's type (add or remove).
     */
    private final Type type;
    
    /**
     * This icon's width and height in pixels.
     */
    private final int size = 8;
    
    /**
     * The width of a stroke (the horizontal and/or vertical line this
     * icon draws) in pixels.
     */
    private final float strokeWidth = 1.49f;
    
    public AddRemoveIcon(Type type) {
        this.type = type;
    }
    
    public int getIconHeight() {
        return size;
    }

    public int getIconWidth() {
        return size;
    }

    /**
     * Paints a "+" or "-" symbol, depending on this icon's type.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        float xf = x;
        float yf = y;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
        Line2D horiz = new Line2D.Float(
                xf,      yf+(size/2f),
                xf+size, yf+(size/2f));
        g2.draw(horiz);
        if (type == Type.ADD) {
            Line2D vert = new Line2D.Float(
                    xf+(size/2f), yf,
                    xf+(size/2f), yf+size);
            g2.draw(vert);
        }
        g2.dispose();
    }

}
