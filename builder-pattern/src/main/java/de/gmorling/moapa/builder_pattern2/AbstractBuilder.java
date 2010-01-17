/**
 *  Copyright 2010 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.gmorling.moapa.builder_pattern2;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

public abstract class AbstractBuilder<T> {

	private static Validator validator = Validation
			.buildDefaultValidatorFactory().getValidator();

	protected abstract T buildInternal();

	public T build() throws ConstraintViolationException {

		T object = buildInternal();

		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		return object;
	}
}
