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

package ca.sqlpower.swingui.event;

/**
 * Interface for any parties interested in being notified of major lifecycle
 * milestones in an application session object's lifetime. Presently, the only
 * milestone of interest is that the session is closing.
 *
 * @param <S> The session type this listener is attached to.
 */
public interface SessionLifecycleListener<S> {
    
    /**
     * This is called when the session is closing.
     */
    public void sessionClosing(SessionLifecycleEvent<S> e);

    /**
     * This is called when the session is opening
     */
    public void sessionOpening(SessionLifecycleEvent<S> e);
}
