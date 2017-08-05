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

import java.lang.reflect.InvocationTargetException;

import javax.annotation.*;

/**
 * Encapsulates reading and/or writing a value of single object property.
 * @param <T> The type of object this property is for.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public interface Property<T, V> extends PropertyValueInfo<V> {

	/**
	 * Returns the name of the property. The name is guaranteed to be a valid Java variable name, and is suitable to be used for serialization.
	 * @return The name of the property.
	 */
	public String getName();

	/** @return Whether the property's value may be read. */
	public boolean isReadable();

	/**
	 * Retrieves the value of the property for a given instance.
	 * @param object The object the property value of which should be returned.
	 * @return The retrieved value of the property on the given object, which may be <code>null</code> if the property allows null values.
	 * @throws UnsupportedOperationException if this property is not readable.
	 * @throws IllegalAccessException If the object is enforcing Java language access control some underlying method is inaccessible.
	 * @throws InvocationTargetException if some underlying method throws an exception.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 */
	public V getValue(@Nonnull T object) throws UnsupportedOperationException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError;

	/** @return Whether the property's value may be read. */
	public boolean isWritable();

	/**
	 * Sets the value of the property for a given instance.
	 * @param object The object the property value of which should be set, which may be <code>null</code> if the property allows null values.
	 * @throws UnsupportedOperationException if this property is not writable.
	 * @throws IllegalArgumentException the given value is inappropriate for the property.
	 * @throws IllegalAccessException If the object is enforcing Java language access control some underlying method is inaccessible.
	 * @throws InvocationTargetException if some underlying method throws an exception.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 */
	public void setValue(@Nonnull T object, V value)
			throws UnsupportedOperationException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError;

}
