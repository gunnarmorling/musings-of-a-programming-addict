package de.gmorling.moapa.jpa2_bv_integration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries( { @NamedQuery(name = Customer.FIND_ALL_CUSTOMERS, query = "SELECT c FROM Customer c") })
public class Customer {

	public final static String FIND_ALL_CUSTOMERS = "findAllCustomers";

	@Id
	@GeneratedValue
	@NotNull
	private Long id;

	@NotNull
	@Size(min = 3, max = 80)
	private String name;

	@AssertTrue(groups = DeletionAttributes.class)
	private boolean archived;

	public Customer() {

	}

	public Customer(String name) {
		this.name = name;
		archived = false;
	}

	// getters and setters ...

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