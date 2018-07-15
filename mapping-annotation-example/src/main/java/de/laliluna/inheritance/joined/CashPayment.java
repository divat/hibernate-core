package de.laliluna.inheritance.joined;

import javax.persistence.Entity;

@Entity
public class CashPayment extends PaymentMethod {


	private String bankAccount;

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Override
	public String toString() {
		return "Cash-Payment: " + getId() + " Name: " + getName() + " Number: "
				+ getBankAccount();
	}
}
