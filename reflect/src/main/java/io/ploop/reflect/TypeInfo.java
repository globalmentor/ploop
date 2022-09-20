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

package io.ploop.reflect;

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
	 * @return Type information for the existing resolved type.
	 */
	public static TypeInfo<?> forResolvedType(@Nonnull final ResolvedType resolvedType) {
		return new TypeInfo<Object>(resolvedType); //TODO cache TypeInfo instances
	}

	/**
	 * Static factory method to create type info from a class.
	 * @param <C> The type represented by the given class.
	 * @param type The class from which to create type information.
	 * @return Type information for the given class.
	 * @see TypeResolver#resolve(java.lang.reflect.Type, java.lang.reflect.Type...)
	 */
	@SuppressWarnings("unchecked")
	public static <C> TypeInfo<C> forClass(@Nonnull final Class<C> type) {
		return (TypeInfo<C>)forResolvedType(TYPE_RESOLVER.resolve(requireNonNull(type))); //TODO cache TypeInfo instances
	}

	/**
	 * Determines if another class can be assigned to this type in the context of reflection. This method is useful for determining if some type is compatible
	 * with a method signature.
	 * <p>
	 * This method functions identically to {@link Class#isAssignableFrom(Class)}, except that it allows the assigned from class to be a non-primitive
	 * representation (e.g. {@link Integer}) of a primitive type (e.g. <code>int</code>).
	 * </p>
	 * <p>
	 * Note that this check is one-way: boxed types are considered reflection assignable to corresponding primitive types, but not vice-versa.
	 * </p>
	 * @param fromClass The class the assignment compatibility of which is questioned.
	 * @return <code>true</code> if the given from class is assignment compatible for reflection with this type.
	 * @see Class#isAssignableFrom(Class)
	 * @see Reflector#isReflectionAssignableFrom(Class, Class)
	 * @see <a href="https://stackoverflow.com/q/11764588/421049"><cite>Reflection on methods with a primitive numeric return type</cite></a>
	 */
	public boolean isReflectionAssignableFrom(@Nonnull final Class<?> fromClass) {
		return Reflector.isReflectionAssignableFrom(getErasedType(), fromClass);
	}

	/**
	 * Determines if an object is an instance of this type in the context of reflection. This method is useful for determining if if an actual return type of a
	 * method is compatible with a method signature.
	 * <p>
	 * This method functions identically to {@link Class#isInstance(Object)}, except that it allows the object to be a non-primitive representation (e.g.
	 * {@link Integer}) of a primitive type (e.g. <code>int</code>).
	 * </p>
	 * @param object The object being checked, which may be <code>null</code>.
	 * @return <code>true</code> if the given object is not <code>null</code> and is assignment compatible for reflection with this type.
	 * @see Class#isInstance(Object)
	 * @see #isReflectionAssignableFrom(Class)
	 * @see Reflector#isReflectionInstance(Object, Class)
	 * @see <a href="https://stackoverflow.com/q/11764588/421049"><cite>Reflection on methods with a primitive numeric return type</cite></a>
	 */
	public boolean isReflectionInstance(@Nullable final Object object) {
		return Reflector.isReflectionInstance(object, getErasedType());
	}

	/**
	 * Casts the given object to this type in the context of reflection. The object must be an instance of this type, a boxed version if this is a primitive type,
	 * or <code>null</code>; otherwise an exception will be thrown. This method is useful for casting the return value of a method, obtained by reflection.
	 * <p>
	 * This method functions identically to {@link Class#cast(Object)}, except that it allows the object to be a non-primitive representation (e.g.
	 * {@link Integer}) of a primitive type (e.g. <code>int</code>).
	 * </p>
	 * @param object The object to be cast, which may be <code>null</code>.
	 * @return The given object, which may be <code>null</code>, cast to this type.
	 * @see Class#cast(Object)
	 * @see #isReflectionInstance(Object)
	 * @see Reflector#castReflection(Object, Class)
	 * @see <a href="https://stackoverflow.com/q/11764588/421049"><cite>Reflection on methods with a primitive numeric return type</cite></a>
	 */
	public T castReflection(@Nullable final Object object) {
		return Reflector.castReflection(object, getErasedType());
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
