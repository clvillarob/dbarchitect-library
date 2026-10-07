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

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import ca.sqlpower.sql.DataSourceCollection;
import ca.sqlpower.sql.SPDataSource;
import ca.sqlpower.util.UserPrompter.UserPromptOptions;
import ca.sqlpower.util.UserPrompter.UserPromptResponse;

public interface UserPrompterFactory {
	
	/**
	 * This describes the type of prompter created by the factory.
	 * The type will tell what kind of response is given by the prompter. 
	 */
    public enum UserPromptType {
    	
    	/**
    	 * Boolean prompt types can have the OK, NOT_OK and CANCEL options
    	 * and is used to ask simple questions of the user. New booleans
    	 * are not allowed.
    	 */
    	BOOLEAN(Boolean.class),
    	
    	/**
    	 * File prompt types can have OK, NEW, NOT_OK, and CANCEL options.
    	 * The file prompts can allow the selection of existing files or create
    	 * a new file 
    	 */
    	FILE(File.class),
    	
    	/**
    	 * Just an informative message that does not solicit any additional
    	 * information from the user. Supports only the OK option type.
    	 */
    	MESSAGE(Void.class),
    	
    	/**
    	 * Text prompt type that supports the OK and CANCEL options. This is used for
    	 * string manipulation and/or replacement.
    	 */
    	TEXT(String.class);
    	
    	private final Class<? extends Object> clazz;

		private UserPromptType(Class<? extends Object> clazz) {
			this.clazz = clazz;
    	}
		
		public Class<? extends Object> getPromptTypeClass() {
			return clazz;
		}
    }

	/**
	 * Creates a new user prompter instance with the given settings. User
	 * prompter instances can be stateful (for example, an "always accept"
	 * button will cause that prompter to continue returning "OK" forever), so
	 * it is important to obtain a new user prompter from this factory for every
	 * overall operation.
	 * 
	 * @param question
	 *            The question the new prompter will pose when solociting a
	 *            response from the user. This question string is not exactly
	 *            plain text: it is formatted according to to rules laid out in
	 *            the {@link MessageFormat} class. The most important
	 *            implications are that the single quote (') character and the
	 *            open curly brace ({) characters are special and have to be
	 *            escaped in order to appear in the message. The other important
	 *            thing (the benefit, that is) is that constructions of the form
	 *            {0} are placeholders that will be substituted every time the
	 *            question is asked via the
	 *            {@link UserPrompter#promptUser(Object[])} method is called.
	 *            See {@link MessageFormat} for details.
	 *            <br>
	 *            Also, UserPrompter implementations will ensure that newline
	 *            characters (\n) show up as new lines when the question is
	 *            presented to the user.<p>
	 * @param responseType
	 *            The object type that will be returned by the prompter.<p>
	 * @param optionType
	 * 			  The combination of responses to be used to prompt the user<p>          
	 * @param defaultResponse
	 *            The response object to use as a default. The default will be
	 *            used to specify the default selection for user interactive
	 *            prompters or it will be the returned value if the selection is
	 *            automatic.<p>
	 * @param buttonNames
	 * 			  An array of strings of an indefinite size which will be used 
	 * 			  as labels for each of the responses. The strings should be stored 
	 * 			  in the array in the order indicated by the optionType. If the number
	 * 			  of strings in the array does not match the number of responses required 
	 * 			  for each optionType, an IllegalStateException will be thrown.
	 * 			  The following are currently the types of strings used:<br>
	 * 		      1)okText<br>
	 *            The text to associate with the OK response. Try to use a word
	 *            or phrase from the question instead of a generic word like
	 *            "OK" or "Yes".<br>
	 *            2)newText<br>
	 *            The text to associate with the NEW response. Try to use a word
	 *            or phrase from the question instead of a generic word like
	 *            "NEW" or "Yes". This action should create an appropriate new
	 *            object.<br>
	 *            3)notOkText<br>
	 *            The text to associate with the "not OK" response. Try to use a
	 *            word or phrase from the question instead of a generic word
	 *            like "No".<br>
	 *            4)cancelText<br>
	 *            The text to associate with response that cancels the whole
	 *            operation.<br>
	 */
    public UserPrompter createUserPrompter(String question, UserPromptType responseType, UserPromptOptions optionType,
			UserPromptResponse defaultResponseType, Object defaultResponse, 
			String... buttonNames);

	/**
	 * This method is almost identical to createUserPrompter but instead it
	 * creates a database user prompter. This is in a separate method because it
	 * takes a collection of datasources as one of its arguments
	 */
    public UserPrompter createDatabaseUserPrompter(String question, List<Class<? extends SPDataSource>> dsTypes,
    		UserPromptOptions optionType, UserPromptResponse defaultResponseType, Object defaultResponse,
    		DataSourceCollection<SPDataSource> dsCollection, String ... buttonNames);
    
    public <T> UserPrompter createListUserPrompter(String question,
			List<T> responses, T defaultResponse);
}
