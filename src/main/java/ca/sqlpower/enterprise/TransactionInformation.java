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
package ca.sqlpower.enterprise;

import java.text.DateFormat;

/**
 * Simple container class for information regarding a particular revision.
 */
public class TransactionInformation {
    
    public final static DateFormat DATE_FORMAT = 
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

    private final long versionNumber;
    private final long timeCreated; //in millis
    private final String versionAuthor;
    private final String versionDescription;
    private final String simpleDescription;
    
    public TransactionInformation(long versionNumber, long timeCreated, 
            String versionAuthor, String versionDescription) {
        this(versionNumber, timeCreated, versionAuthor, versionDescription, versionDescription);
    }
    
    /**
     * Create an TransactionInformation object
     * 
     * @param versionNumber
     * @param timeCreated
     * @param versionAuthor
     * @param versionDescription
     * @param simpleDescription
     */
    public TransactionInformation(long versionNumber, long timeCreated, 
            String versionAuthor, String versionDescription, String simpleDescription) {
        this.versionNumber = versionNumber;
        this.timeCreated = timeCreated;
        this.versionAuthor = versionAuthor;
        this.versionDescription = versionDescription;
        this.simpleDescription = simpleDescription;
    }
    
    /**
     * Returns a formatted list of strings describing this transaction.
     */
    public String toString() {
        return "v" + versionNumber + " (" + DATE_FORMAT.format(timeCreated) + ")" +
                ", " + versionAuthor + ":" + simpleDescription;
    }

    public long getVersionNumber() {
        return versionNumber;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public String getVersionAuthor() {
        return versionAuthor;
    }

    public String getVersionDescription() {
        return versionDescription;
    }
    
    public String getSimpleDescription() {
        return simpleDescription;
    }
    
}