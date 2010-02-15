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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

@SelfValidating
public class CalendarEvent implements Validatable {

	@NotNull
	private final String title;

	private final Date startDate;

	private final Date endDate;

	public CalendarEvent(String title, Date startDate, Date endDate) {

		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public boolean isValid() {
		return startDate == null || endDate == null || startDate.before(endDate);
	}

	@Override
	public String toString() {
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return title + " from " +  (startDate == null ? "-" : format.format(startDate)) + " till " + (endDate == null ? "-" : format.format(endDate));
	}
	

}