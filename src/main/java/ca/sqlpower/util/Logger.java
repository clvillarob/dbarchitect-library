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
package ca.sqlpower.util;
import java.io.PrintStream;

/**
 * A generic logging facility.  In its current implementation, it does
 * barely anything.  In the future, it'll support lots of useful
 * stuff.
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class Logger {

    /**
     * All log output is sent to this stream.
     */
    protected PrintStream out;

    /**
     * This string should be prepended to all log output lines
     */
    protected String initialString;

    /**
     * Constructs a new logger which will direct all of its output to
     * one place.
     *
     * @param out The output stream which will recieve all log messages
     */
    public Logger(PrintStream out, String initialString) {
	this.initialString=initialString;
	this.out=out;
	if(initialString==null || out==null) {
	    throw new NullPointerException();
	}
    }

    /**
     * Constructs a new logger which will direct all of its output to
     * one place.
     *
     * @param out The output stream which will recieve all log messages
     */
    public Logger(PrintStream out) {
	this(out, "");
    }

    public void log(String message) {
	out.print("[1m");
	out.print(initialString);
	out.print(message);
	out.println("[0m");
    }
}
