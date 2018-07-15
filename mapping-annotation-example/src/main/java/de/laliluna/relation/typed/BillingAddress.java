package de.laliluna.relation.typed;

import javax.persistence.Entity;

@Entity
public class BillingAddress extends ReaderAddress {

	
	public BillingAddress() {
		super();
	}

	public BillingAddress(Integer id, String address, String city) {
		super(id,address,city);
	}


}
