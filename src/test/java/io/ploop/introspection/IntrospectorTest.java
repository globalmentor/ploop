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

import static org.junit.Assert.*;

import java.util.Map;

import static org.hamcrest.Matchers.*;

import org.junit.*;

/**
 * Tests the descriptive abilities of the PLOOP introspector.
 * 
 * @author Garret Wilson
 * @see Introspector
 * @see Introspector#INSTANCE
 */
public class IntrospectorTest {

	/** Tests discovering the properties of {@link FooBarBean}. */
	@Test
	public void testFooBarBeanProperties() {
		final Map<String, Property<FooBarBean, ?>> properties = Introspector.INSTANCE.discoverProperties(TypeInfo.forClass(FooBarBean.class));
		assertThat(properties.size(), is(2));

		//foo
		assertThat(properties, hasKey("foo"));
		final Property<FooBarBean, ?> fooProperty = properties.get("foo");
		assertThat(fooProperty.getName(), is("foo"));
		assertThat(fooProperty.isReadable(), is(true));
		assertThat(fooProperty.isWritable(), is(false)); //TODO implement

		//bar
		assertThat(properties, hasKey("bar"));
		final Property<FooBarBean, ?> barProperty = properties.get("bar");
		assertThat(barProperty.getName(), is("bar"));
		assertThat(barProperty.isReadable(), is(true));
		assertThat(barProperty.isWritable(), is(false)); //TODO implement
	}

}
