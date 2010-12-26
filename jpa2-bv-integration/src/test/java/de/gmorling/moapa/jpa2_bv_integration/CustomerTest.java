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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	@BeforeClass
	public static void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("testPU");
	}

	@AfterClass
	public static void closeEntityManagerFactory() {

		if (emf != null) {
			emf.close();
		}
	}

	@Before
	public void beginTransaction() {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}

	@After
	public void rollbackTransaction() {

		if (em == null) {
			return;
		}

		if (em.getTransaction().isActive())
			em.getTransaction().rollback();

		if (em.isOpen())
			em.close();
	}

	@Test
	public void nameTooShortOnInsert() {

		try {
			Customer customer = new Customer("Bo");
			em.persist(customer);
			em.flush();
			fail("Expected ConstraintViolationException wasn't thrown.");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations()
					.iterator().next();
			assertEquals("name", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor()
					.getAnnotation().annotationType());
		}
	}

	@Test
	public void nameTooShortOnUpdate() {

		try {
			Customer customer = new Customer("Bob");
			em.persist(customer);
			em.flush();

			em.detach(customer);
			customer.setName("Bo");
			em.merge(customer);
			em.flush();

			fail("Expected ConstraintViolationException wasn't thrown.");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations()
					.iterator().next();
			assertEquals("name", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor()
					.getAnnotation().annotationType());
		}
	}

	@Test
	public void validCustomer() {

		Customer customer = new Customer("Bob");
		em.persist(customer);
		em.flush();
		Query query = em.createNamedQuery(Customer.FIND_ALL_CUSTOMERS);

		assertEquals(1, query.getResultList().size());
	}

	@Test(expected = ConstraintViolationException.class)
	public void nonArchivedCustomerDeleted() {

		Customer customer = new Customer("Bob");
		em.persist(customer);
		em.remove(customer);
		em.flush();
	}

	@Test
	public void archivedCustomerDeleted() {

		Customer customer = new Customer("Bob");
		em.persist(customer);

		customer.setArchived(true);
		em.remove(customer);
		em.flush();
		assertTrue(em.createNamedQuery(Customer.FIND_ALL_CUSTOMERS)
				.getResultList().isEmpty());
	}
}
