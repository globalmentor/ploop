/*
 * Copyright © 2017 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.ploop.introspect;

import java.util.*;

import javax.annotation.*;

import com.fasterxml.classmate.*;
import com.fasterxml.classmate.members.*;

import io.ploop.reflect.TypeInfo;

/**
 * A factory that discovers properties and other information from an object class.
 * @author Garret Wilson
 */
public class Introspector {

	/** Singleton instance. */
	public static final Introspector INSTANCE = new Introspector();

	private static final MemberResolver memberResolver = new MemberResolver(TypeInfo.TYPE_RESOLVER);

	/** @return The thread-safe member resolver. */
	protected MemberResolver getMemberResolver() {
		return memberResolver;
	}

	/** This class cannot be publicly instantiated. */
	private Introspector() {
	}

	/**
	 * Discovers properties of the given object type.
	 * @param <T> The type of the object of object to be described.
	 * @param objectType Information about type of object to be described.
	 * @return The map of discovered properties, mapped to property name.
	 */
	public <T> Map<String, Property<T, ?>> discoverProperties(@Nonnull final TypeInfo<T> objectType) {
		final Map<String, PropertyReader<T, ?>> propertyReaders = new HashMap<>();
		//TODO add writers

		//get properties from methods
		final ResolvedTypeWithMembers resolvedTypeWithMembers = getMemberResolver().resolve(objectType.getResolvedType(), null, null);
		for(final ResolvedMethod method : resolvedTypeWithMembers.getMemberMethods()) {

			//see if the method is a getter candidate
			if(method.getArgumentCount() == 0 && method.getReturnType() != null) {
				final String propertyName = JavaBeans.getGetterPropertyName(method.getName());
				//TODO use annotations do determine if there should be a different name
				//TODO use annotations do determine if non-public methods should be used
				if(propertyName != null && method.isPublic()) {
					final TypeInfo<?> propertyValueType = TypeInfo.forResolvedType(method.getReturnType());
					propertyReaders.put(propertyName, new GetterMethodPropertyReader<>(propertyValueType, method));
				}
			} else {
				//TODO check for setters
			}
		}
		//TODO get properties from fields

		final Map<String, Property<T, ?>> properties = new HashMap<>();
		//add readable properties
		for(final Map.Entry<String, PropertyReader<T, ?>> propertyReaderEntry : propertyReaders.entrySet()) {
			final String propertyName = propertyReaderEntry.getKey();
			final PropertyReader<T, ?> propertyReader = propertyReaderEntry.getValue();
			//TODO see if there is a writer
			final TypeInfo<?> propertyType = propertyReader.getValueType();
			properties.put(propertyName, createProperty(propertyName, propertyType, propertyReader));
		}
		//TODO add write-only properties
		return properties;
	}

	protected <T, V> Property<T, V> createProperty(@Nonnull final String propertyName, @Nonnull final TypeInfo<V> propertyType,
			@Nonnull final PropertyReader<T, ?> propertyReader /*TODO add writer*/) {
		//TODO make sure the types are compatible
		//TODO wrap the properties with converters as appropriate to ensure the types match
		@SuppressWarnings("unchecked")
		final ReaderWriterProperty<T, V> property = new ReaderWriterProperty<T, V>(propertyName, propertyType, (PropertyReader<T, V>)propertyReader);
		return property;
	}

}
