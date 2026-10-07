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
package ca.sqlpower.sql;

import java.util.Date;

/**
 * The DateFudge class helps to get around a sticky timezone problem
 * in Oracle. It shouldn't have to exist!
 *
 * @author Jonathan Fuerth
 * @version $Id$
 */
public class DateFudge {
    long offsetInMillis;

    /**
     * Creates a new Date Fudge object, set to a specific offset.
     *
     * @param gmtOffset the offset in minutes.  For Eastern time, this
     * would be -300 (5 hours later than GMT).
     */
    public DateFudge(int gmtOffset) {
	offsetInMillis=gmtOffset*60*1000;
    }

    /**
     * Sets the given Date ahead or back by the offset specified when
     * this DateFudge object was created.
     */
    public void fudge(Date date) {
	date.setTime(date.getTime()+offsetInMillis);
    }
}
