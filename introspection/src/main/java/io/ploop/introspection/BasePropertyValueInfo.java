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

import static java.util.Objects.*;

import javax.annotation.*;

/**
 * Base implementation of object property value information.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public abstract class BasePropertyValueInfo<V> implements PropertyValueInfo<V> {

	private final TypeInfo<V> valueType;

	@Override
	public TypeInfo<V> getValueType() {
		return valueType;
	}

	/**
	 * Constructor.
	 * @param valueType The type of value the property represents.
	 */
	public BasePropertyValueInfo(@Nonnull final TypeInfo<V> valueType) {
		this.valueType = requireNonNull(valueType);
	}

}
