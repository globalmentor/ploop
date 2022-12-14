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

import javax.annotation.*;

/**
 * The most basic of JavaBean implementations. A pure bean.
 * 
 * @author Garret Wilson
 */
public class SimpleFooBarBean {

	private String foo;

	private int bar;

	/** No-args constructor. */
	public SimpleFooBarBean() {
	}

	/** @return The foo. */
	public String getFoo() {
		return foo;
	}

	/**
	 * Sets foo.
	 * @param foo The new foo.
	 */
	public void setFoo(@Nullable final String foo) {
		this.foo = foo;
	}

	/** @return The bar. */
	public int getBar() {
		return bar;
	}

	/**
	 * Sets bar.
	 * 
	 * @param bar The new bar.
	 */
	public void setBar(@Nullable final int bar) {
		this.bar = bar;
	}

}
