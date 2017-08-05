/*
 * Copyright © 2017 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ploop.introspection;

import java.util.regex.*;

/**
 * Definitions for JavaBeans property determination.
 * 
 * <p>
 * Much of this code evolved in the <code>com.globalmentor.java.Classes</code> class, which started as <code>com.garretwilson.lang.ClassUtilities</code>.
 * </p>
 * 
 * @author Garret Wilson
 * @see java.beans.Introspector
 */
public class JavaBeans {

	/** The getter prefix "get". */
	public static final String GET_GETTER_PREFIX = "get";

	/** The getter prefix "is". */
	public static final String IS_GETTER_PREFIX = "is";

	/** The getter prefix "set". */
	public static final String SET_SETTER_PREFIX = "set";

	/**
	 * The pattern recognizing a getter method name: "get" or "is" followed by any other characters (assuming they are Java characters), with the prefix in
	 * matching group 1 and the property name in matching group 2.
	 */
	public static final Pattern GETTER_METHOD_NAME_PATTERN = Pattern.compile("(" + GET_GETTER_PREFIX + '|' + IS_GETTER_PREFIX + ")(.+)");

	/**
	 * The pattern recognizing a setter method name: "set" followed by any other characters (assuming they are Java characters), with the prefix in matching group
	 * 1 and the property name in matching group 2.
	 */
	public static final Pattern SETTER_METHOD_NAME_PATTERN = Pattern.compile("(" + SET_SETTER_PREFIX + ")(.+)");

	//property names from methods

	/**
	 * Determines the property name of the given candidate getter method name.
	 * <p>
	 * This implementation converts the method name to a property name using {@link java.beans.Introspector#decapitalize(String)}, which follows JavaBeans rules
	 * for properties. Thus a method such as <code>getURL()</code> would yield the property name <code>URL</code>.
	 * </p>
	 * @param methodName The method name, such as <code>"getPropertyName"</code> or <code>"isPropertyName"</code>.
	 * @return The property name in the form <code><var>propertyName</var></code>, or <code>null</code> if the name of the method is not in the form
	 *         <code>"get<var>PropertyName</var>"</code> or <code>"is<var>PropertyName</var>"</code>.
	 * @see java.beans.Introspector#decapitalize(String)
	 */
	public static String getGetterPropertyName(final String methodName) {
		final Matcher matcher = GETTER_METHOD_NAME_PATTERN.matcher(methodName);
		return matcher.matches() ? java.beans.Introspector.decapitalize(matcher.group(2)) : null;
	}

	/**
	 * Determines the property name of the given candidate setter method name.
	 * <p>
	 * This implementation converts the method name to a property name using {@link java.beans.Introspector#decapitalize(String)}, which follows JavaBeans rules
	 * for properties. Thus a method such as <code>setURL()</code> would yield the property name <code>URL</code>.
	 * </p>
	 * @param methodName The method name, such as <code>"setPropertyName"</code>.
	 * @return The property name in the form <code><var>propertyName</var></code>, or <code>null</code> if the name of the method is not in the form
	 *         <code>"set<var>PropertyName</var>"</code>.
	 */
	public static String getSetterPropertyName(final String methodName) {
		final Matcher matcher = SETTER_METHOD_NAME_PATTERN.matcher(methodName);
		return matcher.matches() ? java.beans.Introspector.decapitalize(matcher.group(2)) : null;
	}

}
