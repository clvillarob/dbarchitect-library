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
package ca.sqlpower.enterprise.client;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import ca.sqlpower.enterprise.client.SPServerInfo;

@Immutable
public class ProjectLocation {

	private final String uuid;
	private final String name;
	private final SPServerInfo serviceInfo;
	
	public ProjectLocation(
			@Nonnull String uuid,
			@Nonnull String name,
			@Nonnull SPServerInfo serviceInfo) {
		
		this.uuid = uuid;
		this.name = name;
		this.serviceInfo = serviceInfo;
	}
	
	public @Nonnull String getName() {
		return name;
	}

	public @Nonnull String getUUID() {
		return uuid;
	}

	public @Nonnull SPServerInfo getServiceInfo() {
		return serviceInfo;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
