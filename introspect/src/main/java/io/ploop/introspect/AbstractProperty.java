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

package io.ploop.introspect;

import static java.util.Objects.*;

import javax.annotation.*;

import io.ploop.reflect.TypeInfo;

/**
 * Abstract base class for implementing a property.
 * @param <T> The type of object this property is for.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public abstract class AbstractProperty<T, V> extends BasePropertyValueInfo<V> implements Property<T, V> {

	private final String name;

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Constructor.
	 * @param name The name of the property.
	 * @param valueType The resolved type of value the property represents.
	 * @throws NullPointerException if the given name is <code>null</code>.
	 * @throws IllegalArgumentException if the given name is not a valid Java variable name.
	 */
	public AbstractProperty(@Nonnull final String name, @Nonnull final TypeInfo<V> valueType) {
		super(valueType);
		this.name = requireNonNull(name);
		//TODO check name validity
	}

}
