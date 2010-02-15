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

package de.gmorling.moapa.self_validating;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * A constraint annotation for the <a
 * href="http://jcp.org/en/jsr/detail?id=303">Bean Validation API</a>, that
 * allows to express constraints using any <a
 * href="http://jcp.org/en/jsr/detail?id=223">JSR 223</a> compatible scripting
 * or expression language.
 * </p>
 * <p>
 * Accepts any type.
 * <p>
 * </p>
 * Based on the similar concept of the <a
 * href="http://oval.sourceforge.net/">OVal framework</a>. </p>
 * 
 * @author Gunnar Morling
 */
@Target( { TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SelfValidatingValidator.class)
@Documented
public @interface SelfValidating {

	String message() default "{de.gmorling.moapa.self_validating.SelfValidating.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}