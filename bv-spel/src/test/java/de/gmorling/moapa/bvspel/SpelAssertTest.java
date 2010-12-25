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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SpelAssertTest {

	@Inject
	private Validator validator;

	private Date startDate;
	private Date endDate;

	@Before
	public void setUpDates() {

		Calendar start = Calendar.getInstance();
		start.set(2010, 13, 24);
		startDate = start.getTime();

		Calendar end = Calendar.getInstance();
		end.set(2010, 13, 26);
		endDate = end.getTime();
	}

	@Test
	public void validatorIsNotNull() {
		assertNotNull(validator);
	}

	@Test
	public void validEvent() {

		CalendarEvent event = new CalendarEvent();
		event.setStartDate(startDate);
		event.setEndDate(endDate);

		assertTrue(validator.validate(event).isEmpty());
	}

	@Test
	public void invalidEvent() {

		CalendarEvent event = new CalendarEvent();
		event.setStartDate(endDate);
		event.setEndDate(startDate);

		Set<ConstraintViolation<CalendarEvent>> violations = validator
				.validate(event);
		assertEquals(1, violations.size());
		ConstraintViolation<CalendarEvent> violation = violations.iterator()
				.next();
		assertEquals(
				"SpEL expression \"startDate < endDate\" didn't evaluate to true.",
				violation.getMessage());
	}

}
