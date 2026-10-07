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

package ca.sqlpower.dao;


/**
 * A persistence exception that signifies that this is not a critical exception
 * but the user should still be notified about the error. These exceptions can
 * be used to roll back the state of the system but still show a nice, friendly
 * prompt to the user instead of the scary red ones with stack traces. 
 */
public class FriendlyRuntimeSPPersistenceException extends RuntimeException {

    /**
     * The message passed into this exception will be displayed to the user
     * so be sure it is user-readable and friendly.
     */
    public FriendlyRuntimeSPPersistenceException(String message) {
        super(message);
    }
}
