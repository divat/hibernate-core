
package de.laliluna.relation.typed;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
@Entity
public class TutorialReader{

	@SequenceGenerator(name = "tutorialreader_seq", sequenceName = "tutorialreader_id_seq")
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tutorialreader_seq")
	private Integer id;

	private String name;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="billing_address_id")
	private BillingAddress billingAddress;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="delivery_address_id")
	private DeliveryAddress deliveryAddress;

	public TutorialReader() {

	}

	public TutorialReader(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BillingAddress getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	public DeliveryAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String toString() {
		return "TutorialReader: " + getId() + " Name: " + getName();
	}
}
