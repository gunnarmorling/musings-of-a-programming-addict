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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * A constraint annotation for the Bean Validation API (JSR 303) which allows
 * constraints to be expressed using Spring's expression language (SpEL).
 * </p>
 * <p>
 * This constraint is in particular useful to express constraints which's
 * validation outcome depend on multiple attributes of the annotated bean in an
 * ad-hoc manner.
 * </p>
 * <p>
 * The following shows the canonical example of a class representing calendar
 * events where the start date always shall be earlier than the end date:
 * </p>
 * 
 * <pre>
 * &#064;SpelAssert(&quot;startDate &lt; endDate&quot;)
 * public class CalendarEvent {
 * 
 * 	private Date startDate;
 * 
 * 	private Date endDate;
 * 
 * 	// ...
 * }
 * </pre>
 * 
 * @author Gunnar Morling
 * 
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SpelAssertValidator.class)
@Documented
public @interface SpelAssert {

	String message() default "{de.gmorling.moapa.bvspel.SpelAssert.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * A SpEL script expression to be evaluated against the annotated object.
	 * This expression must return {@link Boolean#TRUE} if the annotated object
	 * is valid, {@link Boolean#FALSE} otherwise. Any expression returning a non
	 * boolean value will yield in an exception upon validation.
	 * 
	 * @return A SpEL script expression.
	 */
	String value();

	/**
	 * Defines several {@link SpelAssert} annotations on the same element.
	 */
	@Target({ TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		SpelAssert[] value();
	}
}
