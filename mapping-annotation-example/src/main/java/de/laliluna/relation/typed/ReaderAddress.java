
package de.laliluna.relation.typed;

import java.io.Serializable;


import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.STRING)
public class ReaderAddress {


	@Id
	@SequenceGenerator(name = "readeraddress_seq", sequenceName = "readeraddress_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="readeraddress_seq")
	private Integer id;

	private String address;

	private String city;

	public ReaderAddress() {

	}

	public ReaderAddress(Integer id, String address, String city) {
		this.id = id;
		this.address = address;
		this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String toString() {
		return "ReaderAddress: " + getId() + " Address: " + getAddress()
				+ " City: " + getCity();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
