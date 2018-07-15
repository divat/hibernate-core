package de.laliluna.inheritance.singletable;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "plant_type", discriminatorType = DiscriminatorType.STRING)
public class Plant {


    @SequenceGenerator(name = "plant_seq", sequenceName = "plant_id_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plant_seq")
    private Integer id;

    private String name;

    public Plant() {
	super();
    }

    public Plant(Integer id, String name) {
	super();
	this.id = id;
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

    @Override
    public String toString() {
	return "Plant: " + id + " Name:  " + name;
    }

}
