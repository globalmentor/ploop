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

import java.util.*;

import javax.annotation.Nonnull;

/**
 * A description of an object that allows access to a list of properties, allows accessing those property values, and serves as a factory for creating new
 * instances of the described object type.
 * <p>
 * The <dfn>properties</dfn> of an introspection are abstract, named attributes that allow setting and/or retrieving values. A property is accessed via a
 * {@link PropertyAccessor}, and may map to a class field (including private variables) or a getter and/or setting method combination.
 * </p>
 * @param <T> The type of object being described.
 * @author Garret Wilson
 */
public class Introspection<T> {

	/** The immutable map of properties, mapped to property name. */
	private Map<String, Property<T, ?>> propertiesByName;

	private final TypeInfo<T> objectType;

	/** @return The type of object being described. */
	public TypeInfo<T> getObjectType() {
		return objectType;
	}

	/** @return The number of properties the object has. */
	public int getPropertyCount() {
		return propertiesByName.size();
	}

	/**
	 * Indicates whether the object has a certain named property.
	 * @param name The name of the property.
	 * @return <code>true</code> if the object has the indicated property.
	 */
	public boolean hasProperty(@Nonnull final String name) {
		return propertiesByName.containsKey(requireNonNull(name));
	}

	/**
	 * Retrieves a property by name.
	 * @param name The name of the property to retrieve.
	 * @return The named property, or <code>null</code> if there is no property with the given name.
	 */
	public Property<T, ?> getProperty(@Nonnull final String name) {
		return propertiesByName.get(requireNonNull(name));
	}

	/**
	 * Returns the available properties. No particular order is guaranteed.
	 * @return The properties of the object.
	 */
	public Collection<Property<T, ?>> getProperties() {
		return propertiesByName.values();
	}

	/**
	 * Creates a new introspection of the given object type.
	 * @param objectType The type of object to be described.
	 * @return An introspection of the indicated object type.
	 */
	private Introspection(@Nonnull final TypeInfo<T> objectType, @Nonnull final Iterable<Property<T, ?>> properties) {
		this.objectType = requireNonNull(objectType);
		//build a map of properties and store an immutable version of it
		final Map<String, Property<T, ?>> propertiesByName = new HashMap<>();
		for(final Property<T, ?> property : properties) {
			propertiesByName.put(property.getName(), property);
		}
		this.propertiesByName = Collections.unmodifiableMap(propertiesByName);
	}

	/**
	 * Returns an introspection of the given object type.
	 * @param <P> The type of object being described.
	 * @param objectClass The type of object to be described.
	 * @return An introspection of the indicated object type.
	 */
	public static <P> Introspection<P> of(@Nonnull final Class<P> objectClass) {
		//TODO cache introspections 
		final TypeInfo<P> objectType = TypeInfo.forClass(objectClass);
		return new Introspection<>(objectType, Introspector.INSTANCE.discoverProperties(objectType).values());
	}

}
