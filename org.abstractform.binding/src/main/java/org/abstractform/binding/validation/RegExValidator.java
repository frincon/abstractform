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
package org.abstractform.binding.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegExValidator implements Validator<String> {

	private String pattern;
	private String errorMessage;

	public RegExValidator(String pattern) {
		this(pattern, "The value '%s' not match regular expession '%s'");
	}

	public RegExValidator(String pattern, String errorMessage) {
		this.pattern = pattern;
		this.errorMessage = errorMessage;
	}

	@Override
	public List<String> validate(String value) {
		if (value == null) {
			return null;
		} else if (!value.matches(pattern)) {
			List<String> errors = new ArrayList<String>(1);
			errors.add(String.format(errorMessage, value, pattern));
			return Collections.unmodifiableList(errors);
		} else {
			return null;
		}
	}

}
