/*
 * Copyright © 2018 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

package io.ploop.reflect;

import static java.util.Objects.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Enhanced reflection utilities.
 * 
 * @author Garret Wilson
 *
 */
public class Reflector {

	/**
	 * Determines if another class can be assigned to some class in the context of reflection. This method is useful for determining if some type is compatible
	 * with a method signature.
	 * <p>
	 * This method functions identically to {@link Class#isAssignableFrom(Class)}, except that it allows the assigned from class to be a non-primitive
	 * representation (e.g. {@link Integer}) of a primitive type (e.g. <code>int</code>).
	 * <p>
	 * Note that this check is one-way: boxed types are considered reflection assignable to corresponding primitive types, but not vice-versa.
	 * </p>
	 * @param assignedClass The class with which assignment compatibility for reflection is being determined.
	 * @param fromClass The class the assignment compatibility of which is questioned.
	 * @return <code>true</code> if the given from class is assignment compatible for reflection with the assigned class.
	 * @see Class#isAssignableFrom(Class)
	 * @see <a href="https://stackoverflow.com/q/11764588/421049"><cite>Reflection on methods with a primitive numeric return type</cite></a>
	 */
	public static boolean isReflectionAssignableFrom(@Nonnull final Class<?> assignedClass, @Nonnull final Class<?> fromClass) {
		requireNonNull(fromClass);
		if(assignedClass.isPrimitive()) { //if the class is a primitive, we'll have to do special checking
			if(assignedClass == fromClass) { //if the classes are the same
				return true; //they are compatible
			}
			if(Boolean.TYPE == assignedClass) { //otherwise, check to see if a non-primitive version of a primitive class was given
				return fromClass == Boolean.class;
			} else if(Byte.TYPE == assignedClass) {
				return fromClass == Byte.class;
			} else if(Character.TYPE == assignedClass) {
				return fromClass == Character.class;
			} else if(Double.TYPE == assignedClass) {
				return fromClass == Double.class;
			} else if(Float.TYPE == assignedClass) {
				return fromClass == Float.class;
			} else if(Integer.TYPE == assignedClass) {
				return fromClass == Integer.class;
			} else if(Long.TYPE == assignedClass) {
				return fromClass == Long.class;
			} else if(Short.TYPE == assignedClass) {
				return fromClass == Short.class;
			} else if(Void.TYPE == assignedClass) {
				return fromClass == Void.class;
			}
			throw new AssertionError("Unrecognized primitive type: " + assignedClass); //there should be no other primitive types in Java
		}
		return assignedClass.isAssignableFrom(fromClass); //if the class is not primitive, use the default native method
	}

	/**
	 * Determines if an object is an instance of some class in the context of reflection. This method is useful for determining if if an actual return type of a
	 * method is compatible with a method signature.
	 * <p>
	 * This method functions identically to {@link Class#isInstance(Object)}, except that it allows the object to be a non-primitive representation (e.g.
	 * {@link Integer}) of a primitive type (e.g. <code>int</code>).
	 * </p>
	 * @param object The object being checked, which may be <code>null</code>.
	 * @param ofClass The class with which assignment compatibility for reflection is being determined.
	 * @return <code>true</code> if the given object is not <code>null</code> and is assignment compatible for reflection with the indicated class.
	 * @see Class#isInstance(Object)
	 * @see #isReflectionAssignableFrom(Class, Class)
	 * @see <a href="https://stackoverflow.com/q/11764588/421049"><cite>Reflection on methods with a primitive numeric return type</cite></a>
	 */
	public static boolean isReflectionInstance(@Nullable final Object object, @Nonnull final Class<?> ofClass) {
		if(object == null) { //if the object isn't an instance of anything 
			requireNonNull(ofClass); //still perform the precondition check for the class
			return false;
		}
		if(ofClass.isPrimitive()) { //perform special checks for a primitive 
			return isReflectionAssignableFrom(ofClass, object.getClass());
		}
		return ofClass.isInstance(object); //by default perform a normal instance check, which is native and more efficient
	}

	/**
	 * Casts the given object to the indicated type in the context of reflection. The object must be an instance of the indicated class, a boxed version if the
	 * indicated class is a primitive type, or <code>null</code>; otherwise an exception will be thrown. This method is useful for casting the return value of a
	 * method, obtained by reflection.
	 * <p>
	 * This method functions identically to {@link Class#cast(Object)}, except that it allows the object to be a non-primitive representation (e.g.
	 * {@link Integer}) of a primitive type (e.g. <code>int</code>).
	 * </p>
	 * @param <T> The type of object being cast to.
	 * @param object The object to be cast, which may be <code>null</code>.
	 * @param toClass The class representing the type to which the object is being cast.
	 * @return The given object, which may be <code>null</code>, cast to the indicated type.
	 * @see Class#cast(Object)
	 * @see #isReflectionInstance(Object, Class)
	 * @see <a href="https://stackoverflow.com/q/11764588/421049"><cite>Reflection on methods with a primitive numeric return type</cite></a>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castReflection(@Nullable final Object object, @Nonnull final Class<T> toClass) {
		if(object != null) {
			if(!isReflectionInstance(object, toClass)) {
				throw new ClassCastException("Cannot cast " + object.getClass().getName() + " to " + toClass.getName());
			}
		} else { //if the object is null
			requireNonNull(toClass); //still perform the precondition check for the class even if the object is null
		}
		return (T)object;
	}

}
