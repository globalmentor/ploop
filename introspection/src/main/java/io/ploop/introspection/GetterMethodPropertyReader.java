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
import java.util.Objects;

import javax.annotation.*;

import com.fasterxml.classmate.members.ResolvedMethod;

/**
 * A strategy for retrieving the value of an object property. The implementation may access a value directly in a field or via an accessor method.
 * @param <T> The type of object this property is for.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public class GetterMethodPropertyReader<T, V> extends BasePropertyValueInfo<V> implements PropertyReader<T, V> {

	private final ResolvedMethod getterMethod;

	/**
	 * Constructor.
	 * @param valueType The resolved type of value the property represents.
	 * @param getterMethod The method to use for getting the property value .
	 */
	public GetterMethodPropertyReader(@Nonnull final TypeInfo<V> valueType, @Nonnull final ResolvedMethod getterMethod) {
		super(valueType);
		//TODO verify that the getter method has a compatible signature
		this.getterMethod = Objects.requireNonNull(getterMethod);
	}

	@Override
	public V getValue(T object) throws UnsupportedOperationException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError {
		return getValueType().getErasedType().cast(getterMethod.getRawMember().invoke(object));
	}

}
