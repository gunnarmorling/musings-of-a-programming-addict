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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class SelfValidatingTest {

	private static Validator validator;

	private static Date startDate;

	private static Date endDate;

	@BeforeClass
	public static void setUpValidatorAndDates() {

		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();

		startDate = new GregorianCalendar(2009, 8, 20).getTime();
		endDate = new GregorianCalendar(2009, 8, 21).getTime();
	}

	@Test
	public void calendarEventIsValidAsEndDateIsAfterStartDate() {

		CalendarEvent testEvent = new CalendarEvent("Team meeting", startDate,
				endDate);

		assertTrue(validator.validate(testEvent).isEmpty());
	}

	@Test
	public void calendarEventIsInvalidAsEndDateIsBeforeStartDate() {

		CalendarEvent testEvent = new CalendarEvent("Team meeting", endDate,
				startDate);

		Set<ConstraintViolation<CalendarEvent>> constraintViolations = validator
				.validate(testEvent);
		assertEquals(1, constraintViolations.size());

		assertEquals("Validatable object couldn't be validated successfully.",
				constraintViolations.iterator().next().getMessage());
	}

	@Test
	public void calendarEventIsInvalidAsEndDateIsBeforeStartDateWithCustomizedErrorMessage() {

		CalendarEventWithErrorMessage testEvent = new CalendarEventWithErrorMessage(
				"Team meeting", endDate, startDate);

		Set<ConstraintViolation<CalendarEventWithErrorMessage>> constraintViolations = validator
				.validate(testEvent);
		assertEquals(1, constraintViolations.size());

		assertEquals("End date of event must be after start date.",
				constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void testToString() {
		
		CalendarEvent testEvent = new CalendarEvent(
				"Team meeting", startDate, endDate);
		
		assertEquals("Team meeting from 20.09.2009 till 21.09.2009",  testEvent.toString());
	}

}