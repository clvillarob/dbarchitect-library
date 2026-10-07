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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * The StreamLogger takes any InputStream and reads from it continuously until
 * it reaches end-of-file.  Every time a newline is encountered, it will emit a
 * logger message with that text, at the given severity level.
 */
public class StreamLogger extends Thread {
	
	/**
	 * The logger that gets all the messages from the stream.
	 */
	private final Logger logger;
	
	/**
	 * The input stream being translated into log messages.
	 */
	private final InputStream is;

	/**
	 * The logging level all log messages are emitted at.
	 */
	private final Level priority;

	/**
	 * Creates a new StreamLogger that monitors the given input stream and
	 * outputs its text one line at a time to the given logger at the given
	 * priority.
	 * <p>
	 * Remember to start this logger with the start() method if you want it
	 * to run in the background.
	 * 
	 * @param is The stream to watch.
	 * @param logger The logger to log with.
	 * @param priority The priority to log at.
	 */
	public StreamLogger(InputStream is, Logger logger, Level priority) {
		this.is = is;
		this.logger = logger;
		this.priority = priority;
	}

	/**
	 * Enters the main loop, stopping only when EOF is reached on the input stream
	 * or there is an IO Exception.  Remember that if you call this method directly,
	 * this will happen on the calling thread.  You probably want to call start()
	 * instead, which will invoke this run() method on a new thread.
	 */
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.log(priority, line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}