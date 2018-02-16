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

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import org.junit.*;

import io.ploop.introspect.Introspection;
import io.ploop.introspect.Property;

/**
 * Tests retrieving and using an introspection for a type.
 * 
 * @author Garret Wilson
 * @see Introspection
 */
public class IntrospectionTest {

	/** Tests retrieving an introspection of {@link SimpleFooBarBean}. */
	@Test
	public void testFooBarBeanIntrospection() {
		final Introspection<SimpleFooBarBean> fooBarBeanIntrospection = Introspection.of(SimpleFooBarBean.class);
		assertThat(fooBarBeanIntrospection.getPropertyCount(), is(2));

		//foo
		assertThat(fooBarBeanIntrospection.hasProperty("foo"), is(true));
		final Property<SimpleFooBarBean, ?> fooProperty = fooBarBeanIntrospection.getProperty("foo");
		assertThat(fooProperty.getName(), is("foo"));
		assertThat(fooProperty.isReadable(), is(true));
		assertThat(fooProperty.isWritable(), is(false)); //TODO implement

		//bar
		assertThat(fooBarBeanIntrospection.hasProperty("bar"), is(true));
		final Property<SimpleFooBarBean, ?> barProperty = fooBarBeanIntrospection.getProperty("bar");
		assertThat(barProperty.getName(), is("bar"));
		assertThat(barProperty.isReadable(), is(true));
		assertThat(barProperty.isWritable(), is(false)); //TODO implement
	}

	/** Tests getting properties from of {@link SimpleFooBarBean}. */
	@Test
	public void testFooBarBeanGetProperty() throws ReflectiveOperationException {
		final SimpleFooBarBean fooBarbean = new SimpleFooBarBean();
		fooBarbean.setFoo("test");
		fooBarbean.setBar(123);

		final Introspection<SimpleFooBarBean> fooBarBeanIntrospection = Introspection.of(SimpleFooBarBean.class);
		assertThat(fooBarBeanIntrospection.getProperty("foo").getValue(fooBarbean), is("test"));
		assertThat(fooBarBeanIntrospection.getProperty("bar").getValue(fooBarbean), is(123));
	}

}
