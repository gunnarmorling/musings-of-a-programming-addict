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
package de.gmorling.moapa.jpa2_bv_integration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.gmorling.moapa.jpa2_bv_integration.groups.CheckOnDeletion;
import de.gmorling.moapa.jpa2_bv_integration.groups.CheckOnUpdate;

@Entity
@NamedQueries({ @NamedQuery(name = Customer.FIND_ALL_CUSTOMERS, query = "SELECT c FROM Customer c") })
public class Customer {

	public final static String FIND_ALL_CUSTOMERS = "findAllCustomers";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(groups = CheckOnUpdate.class)
	private Long id;

	@NotNull
	@Size(min = 3, max = 80)
	private String name;

	@AssertTrue(groups = CheckOnDeletion.class)
	private boolean archived;

	public Customer() {

	}

	public Customer(String name) {
		this.name = name;
		archived = false;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

}