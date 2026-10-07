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

/**
 * Classes that implement this interface shall keep track of SPSwingWorkers
 * registered with it. The main use case for this is to allow application
 * sessions to cancel running SPSwingWorker threads when they close. 
 */
public interface SwingWorkerRegistry {
    /**
     * Makes the session aware of the given ArchitectSwingWorker instance.
     * When the session dies, it can then tell the ArchitectSwingWorker
     * instances it keeps track of to stop running. 
     */
    public void registerSwingWorker(SPSwingWorker worker);

    /**
     * Removes knowledge of this ArchitectSwingWorker from this session.
     * This should only happen when the ArchitectSwingWorker is finished
     * before the session is closed.
     */
    public void removeSwingWorker(SPSwingWorker worker); 
}
