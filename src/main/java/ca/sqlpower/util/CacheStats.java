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

public class CacheStats {
	protected int totalInserted;
	protected int totalRequested;
	protected int totalHits;
	protected int totalMisses;

	public CacheStats() {
	}

	public void cacheFlush() {
		totalInserted = 0;
		totalRequested = 0;
		totalHits = 0;
		totalMisses = 0;
	}

	public int getTotalInserted() {
		return totalInserted;
	}

	public int getTotalRequested() {
		return totalRequested;
	}

	public int getTotalHits() {
		return totalHits;
	}

	public int getTotalMisses() {
		return totalMisses;
	}

	/**
	 * Returns a number between 0 and 1 indicating the cache hit
	 * ratio.  0 is worst (no hits); 1 is best but unachievable unless
	 * the cache is pre-populated.
	 */
	public double getHitRatio() {
		if (totalHits == 0) return 0.0;
		else return ((double) totalHits) / ((double) totalRequested);
	}
}
