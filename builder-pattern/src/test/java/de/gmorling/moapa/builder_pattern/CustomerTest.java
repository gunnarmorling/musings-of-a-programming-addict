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

package de.gmorling.moapa.builder_pattern;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import javax.validation.ConstraintViolationException;

import org.junit.Test;

import de.gmorling.moapa.builder_pattern.Customer;

public class CustomerTest {

	@Test
	public void validCustomer() {
		Customer c = new Customer.Builder(1, "Smith").firstName("Bob")
				.birthday(new GregorianCalendar(1970, 3, 10).getTime()).build();

		assertNotNull(c);
	}

	@Test
	public void lastNameNullAndBirthdayInFuture() {
		try {
			new Customer.Builder(1, null).birthday(
					new GregorianCalendar(2020, 3, 10).getTime()).build();
			fail("Expected ConstraintViolationException wasn't thrown.");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
		}
	}
}
