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

import java.util.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the reflection and type functionality of the PLOOP reflector.
 * 
 * @author Garret Wilson
 * @see Reflector
 */
public class ReflectorTest {

	/** @see Reflector#isReflectionAssignableFrom(Class, Class) */
	@Test
	public void testIsReflectionAssignableFrom() {
		assertThat(Reflector.isReflectionAssignableFrom(String.class, String.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(String.class, Number.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Number.class, String.class), is(false));

		assertThat(Reflector.isReflectionAssignableFrom(Number.class, Number.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Number.class, Integer.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Integer.class, Number.class), is(false));

		assertThat(Reflector.isReflectionAssignableFrom(Boolean.class, Boolean.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Byte.class, Byte.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Character.class, Character.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Double.class, Double.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Float.class, Float.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Integer.class, Integer.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Long.class, Long.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Short.class, Short.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Void.class, Void.class), is(true));

		//boxed classes are accepted for primitives
		assertThat(Reflector.isReflectionAssignableFrom(boolean.class, Boolean.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(byte.class, Byte.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(char.class, Character.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(double.class, Double.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(float.class, Float.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(int.class, Integer.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(long.class, Long.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(short.class, Short.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(void.class, Void.class), is(true));

		//primitives are _not_ accepted for boxed types
		assertThat(Reflector.isReflectionAssignableFrom(Boolean.class, boolean.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Byte.class, byte.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Character.class, char.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Double.class, double.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Float.class, float.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Integer.class, int.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Long.class, long.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Short.class, short.class), is(false));
		assertThat(Reflector.isReflectionAssignableFrom(Void.class, void.class), is(false));

		assertThat(Reflector.isReflectionAssignableFrom(Hashtable.class, Properties.class), is(true));
		assertThat(Reflector.isReflectionAssignableFrom(Properties.class, Hashtable.class), is(false));

	}

	/** @see Reflector#isReflectionInstance(Object, Class) */
	@Test
	public void testIsReflectionInstance() {
		assertThat(Reflector.isReflectionInstance(null, String.class), is(false));
		assertThat(Reflector.isReflectionInstance(null, Number.class), is(false));
		assertThat(Reflector.isReflectionInstance(null, Integer.class), is(false));
		assertThat(Reflector.isReflectionInstance(null, int.class), is(false));
		assertThat(Reflector.isReflectionInstance("foo", String.class), is(true));
		assertThat(Reflector.isReflectionInstance("foo", Number.class), is(false));
		assertThat(Reflector.isReflectionInstance("foo", Integer.class), is(false));
		assertThat(Reflector.isReflectionInstance("foo", int.class), is(false));
		assertThat(Reflector.isReflectionInstance(Integer.valueOf(123), Number.class), is(true));
		assertThat(Reflector.isReflectionInstance(Integer.valueOf(123), Integer.class), is(true));
		assertThat(Reflector.isReflectionInstance(Integer.valueOf(123), int.class), is(true));

		assertThat(Reflector.isReflectionInstance(new Properties(), Hashtable.class), is(true));
		assertThat(Reflector.isReflectionInstance(new Hashtable<>(), Properties.class), is(false));
	}

	/** @see Reflector#castReflection(Object, Class) */
	@Test
	public void testCastReflection() {
		Reflector.castReflection(null, String.class);

		Reflector.castReflection(null, String.class);
		Reflector.castReflection(null, Number.class);
		Reflector.castReflection(null, Integer.class);
		Reflector.castReflection(null, int.class);
		Reflector.castReflection("foo", String.class);

		Reflector.castReflection(Integer.valueOf(123), Number.class);
		Reflector.castReflection(Integer.valueOf(123), Integer.class);
		Reflector.castReflection(Integer.valueOf(123), int.class);
		Reflector.castReflection(Long.valueOf(123), Long.class);

		Reflector.castReflection(new Properties(), Hashtable.class);
	}

	/** @see Reflector#castReflection(Object, Class) */
	@Test
	public void testCastReflectionToUnrelatedTypeThrowsException() {
		assertThrows(ClassCastException.class, () -> Reflector.castReflection("foo", Number.class));
	}

	/** @see Reflector#castReflection(Object, Class) */
	@Test
	public void testCastReflectionToSubtypeThrowsException() {
		assertThrows(ClassCastException.class, () -> Reflector.castReflection(new Hashtable<>(), Properties.class));
	}
}
