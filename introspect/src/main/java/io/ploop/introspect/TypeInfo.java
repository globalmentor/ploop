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

import com.fasterxml.classmate.*;

/**
 * A value class for representing a ClassMate {@link ResolvedType} along with its original generics-erased type.
 * <p>
 * This class is a lightweight wrapper for {@link ResolvedType} that regains the captured generics that are not available from
 * {@link ResolvedType#getErasedType()}.
 * </p>
 * @param <T> The type of value represented.
 * @author Garret Wilson
 * @see ResolvedType
 */
public class TypeInfo<T> {

	/** The shared singleton instance of the ClassMate type resolver. */
	public static final TypeResolver TYPE_RESOLVER = new TypeResolver();

	private final ResolvedType resolvedType;

	/** @return The resolved type. */
	public ResolvedType getResolvedType() {
		return resolvedType;
	}

	/** @return The original type with runtime generics information erased. */
	@SuppressWarnings("unchecked")
	public Class<T> getErasedType() {
		return (Class<T>)getResolvedType().getErasedType();
	}

	/**
	 * Constructor.
	 * <p>
	 * Unfortunately {@link ResolvedType} discards captured generics, so there is no compiled-time protection of passing the correct type.
	 * </p>
	 * @param valueType The resolved type of value the property represents.
	 */
	private TypeInfo(@Nonnull final ResolvedType valueType) {
		this.resolvedType = requireNonNull(valueType);
	}

	/**
	 * Static factory method to create type info from an existing {@link ResolvedType}.
	 * @param resolvedType The class from which to create type information.
	 */
	public static <C> TypeInfo<C> forResolvedType(@Nonnull final ResolvedType resolvedType) {
		return new TypeInfo<C>(resolvedType); //TODO cache TypeInfo instances
	}

	/**
	 * Static factory method to create type info from a class.
	 * @param type The class from which to create type information.
	 * @see TypeResolver#resolve(java.lang.reflect.Type, java.lang.reflect.Type...)
	 */
	public static <C> TypeInfo<C> forClass(@Nonnull final Class<C> type) {
		return forResolvedType(TYPE_RESOLVER.resolve(requireNonNull(type))); //TODO cache TypeInfo instances
	}

	@Override
	public int hashCode() {
		return getResolvedType().hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if(this == object) {
			return true;
		}
		if(!(object instanceof TypeInfo)) {
			return false;
		}
		return getResolvedType().equals(((TypeInfo<?>)object).getResolvedType());
	}

}
