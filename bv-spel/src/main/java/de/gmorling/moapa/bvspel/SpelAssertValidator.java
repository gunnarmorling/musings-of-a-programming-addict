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
package de.gmorling.moapa.bvspel;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

/**
 * Implements the validation logic for the {@link SpelAssert} constraint
 * annotation. Uses a {@link ExpressionParser} to parse the given SpEL
 * expression and evaluates the same using the annotated object as root object.
 * 
 * @author Gunnar Morling
 * 
 */
public class SpelAssertValidator implements
		ConstraintValidator<SpelAssert, Object> {

	@Inject
	private ExpressionParser parser;

	private Expression expression;

	@Override
	public void initialize(SpelAssert constraintAnnotation) {

		String rawExpression = constraintAnnotation.value();

		if (rawExpression == null) {
			throw new IllegalArgumentException("The expression specified in @"
					+ SpelAssert.class.getSimpleName() + " must not be null.");
		}

		expression = parser.parseExpression(rawExpression);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		if (value == null) {
			return true;
		}

		return Boolean.TRUE.equals(expression.getValue(value, Boolean.class));
	}
}
