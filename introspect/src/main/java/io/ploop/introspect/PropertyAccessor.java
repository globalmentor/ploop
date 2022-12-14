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

/**
 * A strategy for accessing an object property. The implementation may access a value directly in a field or via an accessor method.
 * @param <T> The type of object this property is for.
 * @param <V> The type of value stored in the property.
 * @author Garret Wilson
 */
public interface PropertyAccessor<T, V> extends PropertyValueInfo<V> {

}
