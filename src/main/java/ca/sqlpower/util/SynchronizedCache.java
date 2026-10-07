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

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class SynchronizedCache implements Cache, java.io.Serializable {

	protected Cache cache;

	public SynchronizedCache(Cache cache) {
		this.cache = cache;
	}

	// CACHE-SPECIFIC METHODS
	public synchronized void setMaxMembers(int argMaxMembers) { cache.setMaxMembers(argMaxMembers); }
	public synchronized int getMaxMembers() { return cache.getMaxMembers(); }
	public synchronized Date getLastFlushDate() { return cache.getLastFlushDate(); }
	public synchronized void flush() { cache.flush(); }
	public synchronized CacheStats getStats() { return cache.getStats(); }

	// MAP METHODS (copied from j2sdk 1.3.1 source code Collections.java)
	private Object mutex = this;
	public int size() {
	    synchronized(mutex) {return cache.size();}
	}
	public boolean isEmpty(){
	    synchronized(mutex) {return cache.isEmpty();}
	}
	public boolean containsKey(Object key) {
	    synchronized(mutex) {return cache.containsKey(key);}
	}
	public boolean containsValue(Object value){
	    synchronized(mutex) {return cache.containsValue(value);}
	}
	public Object get(Object key) {
	    synchronized(mutex) {return cache.get(key);}
	}
	
	public Object put(Object key, Object value) {
	    synchronized(mutex) {return cache.put(key, value);}
	}
	public Object remove(Object key) {
	    synchronized(mutex) {return cache.remove(key);}
	}
	public void putAll(Map map) {
	    synchronized(mutex) {cache.putAll(map);}
	}
	public void clear() {
	    synchronized(mutex) {cache.clear();}
	}
	
	private transient Set keySet = null;
	private transient Set entrySet = null;
	private transient Collection values = null;
	
	public Set keySet() {	
		throw new UnsupportedOperationException("This is not practical to implement");
	}
	
	public Set entrySet() {
		throw new UnsupportedOperationException("This is not practical to implement");
	}
	
	public Collection values() {
		throw new UnsupportedOperationException("This is not practical to implement");
	}
	
	public boolean equals(Object o) {
		synchronized(mutex) {return cache.equals(o);}
	}
	public int hashCode() {
		synchronized(mutex) {return cache.hashCode();}
	}
	public String toString() {
	    synchronized(mutex) {return cache.toString();}
	}	
}
