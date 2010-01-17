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

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Customer {

	private long id;
	private String firstName;
	private String lastName;
	private Date birthday;

	private Customer(Builder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.birthday = builder.birthday;
	}

	public static class Builder extends AbstractBuilder<Customer> {

		private long id;

		private String firstName;

		private String lastName;

		private Date birthday;

		public Builder(long id, String lastName) {
			this.id = id;
			this.lastName = lastName;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder birthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

		@Override
		protected Customer buildInternal() {
			return new Customer(this);
		}

	}

	@Min(1)
	public long getId() {
		return id;
	}

	@Size(min = 3, max = 80)
	public String getFirstName() {
		return firstName;
	}

	@Size(min = 3, max = 80)
	@NotNull
	public String getLastName() {
		return lastName;
	}

	@Past
	public Date getBirthday() {
		return birthday;
	}

}