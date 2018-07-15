
package de.laliluna.inheritance.singletable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Garden {


    @SequenceGenerator(name = "garden_seq", sequenceName = "garden_id_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garden_seq")
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "garden_plant_id")
    private Set<Plant> plants = new HashSet<Plant>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "garden_flower_id")
    private Set<Flower> flowers = new HashSet<Flower>();

    public Garden() {

    }

    public Garden(Integer id, String name) {
	this.id = id;
	this.name = name;
    }

    public Garden(String name) {
	this.name = name;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Set<Plant> getPlants() {
	return plants;
    }

    public void setPlants(Set<Plant> plants) {
	this.plants = plants;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String toString() {
	return "Garden: " + getId() + " Name: " + getName();
    }

    public Set<Flower> getFlowers() {
	return flowers;
    }

    public void setFlowers(Set<Flower> flowers) {
	this.flowers = flowers;
    }
}
