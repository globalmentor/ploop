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

import java.lang.reflect.InvocationTargetException;

import javax.annotation.*;

import io.ploop.reflect.TypeInfo;

/**
 * Encapsulates accessors for reading and/or writing a value of single object property.
 * @param <T> The type of object this property is for.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public class ReaderWriterProperty<T, V> extends AbstractProperty<T, V> {

	private final PropertyReader<T, V> reader;

	/**
	 * Constructor.
	 * @param name The name of the property.
	 * @param valueType The resolved type of value the property represents.
	 * @throws NullPointerException if the given name is <code>null</code>.
	 * @throws IllegalArgumentException if the given name is not a valid Java variable name.
	 * @throws IllegalArgumentException if neither a reader nor a writer is give (the property must be readable or writable).
	 */
	public ReaderWriterProperty(@Nonnull final String name, @Nonnull final TypeInfo<V> valueType,
			@Nullable final PropertyReader<T, V> reader /*TODO add writer.*/) {
		super(name, valueType);
		this.reader = reader;
		if(reader == null /*TODO && writer ==null*/) { //make sure either a reader or a writer was passed
			throw new IllegalArgumentException(String.format("Property %s must have a reader or a writer.", name));
		}
	}

	@Override
	public boolean isReadable() {
		return reader != null;
	}

	@Override
	public V getValue(final T object) throws UnsupportedOperationException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError {
		if(reader == null) {
			throw new UnsupportedOperationException(String.format("Property %s does not support reading.", getName()));
		}
		return reader.getValue(object);
	}

	@Override
	public boolean isWritable() {
		return false; //TODO implement
	}

	@Override
	public void setValue(final T object, final V value)
			throws UnsupportedOperationException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, ExceptionInInitializerError {
		//TODO implement
		throw new UnsupportedOperationException(String.format("Property %s does not support writing.", getName()));
	}

}
