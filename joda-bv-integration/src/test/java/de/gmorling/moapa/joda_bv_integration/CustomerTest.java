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
package de.gmorling.moapa.joda_bv_integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.time.DateMidnight;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerTest {

	private static Validator validator;

	@BeforeClass
	public static void setUpValidatorAndDates() {

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void customerWithPastBirthdayCausesNoConstraintViolation() {
		
		Customer customer = new Customer("Bob", new DateMidnight(1960, 11, 3));
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
		
		assertTrue(constraintViolations.isEmpty());
	}
	
	@Test
	public void customerWithFutureBirthdayCausesConstraintViolation() {
		
		Customer customer = new Customer("Bob", new DateMidnight(2020, 11, 3));
		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
		
		assertEquals(1, constraintViolations.size());
		assertEquals("must be in the past", constraintViolations.iterator().next().getMessage());
	}

}
