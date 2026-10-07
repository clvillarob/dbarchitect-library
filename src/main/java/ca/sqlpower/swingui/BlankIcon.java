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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

/**
 * An icon that takes up space but doesn't paint anything. This is helpful, for
 * example, in a tree cell renderer where some of the items don't have icons and
 * some do. Without this placeholder, the tree items will not appear to nest
 * properly.
 */
public class BlankIcon implements Icon {
    
    private static final Map<Dimension, BlankIcon> instances = new HashMap<Dimension, BlankIcon>();
    
    /**
     * This icon's width and height in pixels.
     */
    private final Dimension size;
    
    /**
     * Returns a BlankIcon with the given dimensions. If there was already
     * a BlankIcon created with the requested size, that instance will be
     * returned. Otherwise, a new one will be created for you.
     * 
     * @param width The width of the icon
     * @param height The height of the icon
     */
    public static BlankIcon getInstance(int width, int height) {
        Dimension size = new Dimension(width, height);
        BlankIcon instance = instances.get(size);
        if (instance == null) {
            instance = new BlankIcon(size);
            instances.put(size, instance);
        }
        return instance;
    }
    
    /**
     * Use {@link #getInstance(int, int)} to get an instance of this class.
     */
    private BlankIcon(Dimension size) {
        this.size = size;
    }
    
    public int getIconHeight() {
        return size.height;
    }

    public int getIconWidth() {
        return size.width;
    }

    /**
     * Does nothing. This is a blank icon, remember?
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // no op!
    }

}
