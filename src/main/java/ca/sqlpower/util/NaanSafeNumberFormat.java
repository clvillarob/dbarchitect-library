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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * 
 * NaanSafeDecimalFormat
 *
 * The default unicode character for NaN doesn't render nicely 
 * on some platforms.  Replace it with "NaN" (and when we have time,
 * with a localized string for NaN);
 * 
 * Bear in mind that this will not parse back nicely!
 * 
 */
public class NaanSafeNumberFormat extends DecimalFormat {
	public NaanSafeNumberFormat(String theFormat) {
		super(theFormat);
		DecimalFormatSymbols dfs = getDecimalFormatSymbols();
		dfs.setNaN("NaN"); // FIXME: make this a localized String when we have more time
		setDecimalFormatSymbols(dfs);
	}
}
