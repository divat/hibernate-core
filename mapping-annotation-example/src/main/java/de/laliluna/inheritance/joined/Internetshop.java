
package de.laliluna.inheritance.joined;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Internetshop {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "internetshop_seq")
    @SequenceGenerator(name = "internetshop_seq", sequenceName = "internetshop_id_seq")
    private Integer id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "shop_paymentmethod", joinColumns = { @JoinColumn(name = "shop_id") }, inverseJoinColumns = { @JoinColumn(name = "payment_id") })
    private Set<PaymentMethod> paymentMethods = new HashSet<PaymentMethod>();

    public Internetshop() {

    }

    public Internetshop(String name) {
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

    public Set<PaymentMethod> getPaymentMethods() {
	return paymentMethods;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
	this.paymentMethods = paymentMethods;
    }

    public String toString() {
	return "Internetshop: " + getId() + " Name: " + getName();
    }
}
