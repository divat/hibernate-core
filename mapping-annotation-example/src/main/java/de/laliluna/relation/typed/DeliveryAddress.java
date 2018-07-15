package de.laliluna.relation.typed;

import javax.persistence.Entity;

@Entity
public class DeliveryAddress extends ReaderAddress {

	public DeliveryAddress(){
		super();
	}
	
	public DeliveryAddress(Integer id, String address, String city) {
		super(id,address,city);
	}

}
