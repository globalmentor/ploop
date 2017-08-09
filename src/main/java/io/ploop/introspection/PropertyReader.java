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
 * A strategy for retrieving the value of an object property. The implementation may access a value directly in a field or via an accessor method.
 * @param <T> The type of object this property is for.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public interface PropertyReader<T, V> extends PropertyAccessor<T, V> {

	/**
	 * Retrieves the value of the property for a given instance.
	 * @param object The object the property value of which should be returned.
	 * @return The retrieved value of the property on the given object.
	 * @throws UnsupportedOperationException if this property is not readable.
	 * @throws IllegalAccessException If the object is enforcing Java language access control some underlying method is inaccessible.
	 * @throws InvocationTargetException if some underlying method throws an exception.
	 * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
	 */
	public V getValue(@Nonnull final T object)
			throws UnsupportedOperationException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError;

}
