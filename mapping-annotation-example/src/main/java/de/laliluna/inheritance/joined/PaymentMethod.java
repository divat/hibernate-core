package de.laliluna.inheritance.joined;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PaymentMethod {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paymentmethod_seq")
    @SequenceGenerator(name = "paymentmethod_seq", sequenceName = "paymentmethod_id_seq")
    private Integer id;

    private String name;

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

    @Override
    public String toString() {
	return "PaymentMethod: " + getId() + " Name: " + getName();
    }

}
