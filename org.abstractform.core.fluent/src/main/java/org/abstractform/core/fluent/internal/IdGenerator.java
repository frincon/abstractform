/**
 * Copyright 2014 Fernando Rincon Martin <frm.rincon@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.abstractform.core.fluent.internal;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

	public static final IdGenerator INSTANCE = new IdGenerator();
	public static final String ID_PREFIX = "NULL_NOT_DEFINED_ID_";

	private AtomicLong atomicLong;

	private IdGenerator() {
		atomicLong = new AtomicLong();
	}

	public String getId() {
		return ID_PREFIX + atomicLong.getAndIncrement();
	}

}
