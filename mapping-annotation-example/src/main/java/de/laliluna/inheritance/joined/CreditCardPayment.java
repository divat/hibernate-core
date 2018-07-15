package de.laliluna.inheritance.joined;

import javax.persistence.Entity;

@Entity
public class CreditCardPayment extends PaymentMethod {

	private String creditCardNumber;

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCartNumber) {
		this.creditCardNumber = creditCartNumber;
	}

	@Override
	public String toString() {
		return "Cred-Card: " +getId()+" Name: "+getName()+" Number: "+getCreditCardNumber();
	}
}
