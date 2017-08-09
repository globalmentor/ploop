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

import static org.hamcrest.Matchers.*;

import org.junit.*;

/**
 * Tests retrieving and using an object description.
 * 
 * @author Garret Wilson
 * @see ObjectDescription
 */
public class ObjectDescriptionTest {

	/** Tests retrieving a description of {@link FooBarBean}. */
	@Test
	public void testFooBarBeanDescription() {
		final ObjectDescription<FooBarBean> fooBarBeanDescription = ObjectDescription.of(FooBarBean.class);
		assertThat(fooBarBeanDescription.getPropertyCount(), is(2));

		//foo
		assertThat(fooBarBeanDescription.hasProperty("foo"), is(true));
		final Property<FooBarBean, ?> fooProperty = fooBarBeanDescription.getProperty("foo");
		assertThat(fooProperty.getName(), is("foo"));
		assertThat(fooProperty.isReadable(), is(true));
		assertThat(fooProperty.isWritable(), is(false)); //TODO implement

		//bar
		assertThat(fooBarBeanDescription.hasProperty("bar"), is(true));
		final Property<FooBarBean, ?> barProperty = fooBarBeanDescription.getProperty("bar");
		assertThat(barProperty.getName(), is("bar"));
		assertThat(barProperty.isReadable(), is(true));
		assertThat(barProperty.isWritable(), is(false)); //TODO implement
	}

	/** Tests getting properties from of {@link FooBarBean}. */
	@Test
	public void testFooBarBeanGetProperty() throws ReflectiveOperationException {
		final FooBarBean fooBarbean = new FooBarBean();
		fooBarbean.setFoo("test");
		fooBarbean.setBar(123);

		final ObjectDescription<FooBarBean> fooBarBeanDescription = ObjectDescription.of(FooBarBean.class);
		assertThat(fooBarBeanDescription.getProperty("foo").getValue(fooBarbean), is("test"));
		//TODO fix autoboxing/conversion assertThat(fooBarBeanDescription.getProperty("bar").getValue(fooBarbean), is(123));
	}

}
