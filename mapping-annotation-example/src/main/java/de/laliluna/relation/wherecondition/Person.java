package de.laliluna.relation.wherecondition;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sebastian Hennebrueder
 *         Date: 07.03.2010
 */
@Entity
public class Person {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	@OneToMany
	@JoinColumn
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@Where(clause = "deleted='false'")
	private Set<Car> cars = new HashSet<Car>();

	@ManyToMany
	@JoinTable
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@WhereJoinTable(clause = "deleted='false'")
	private Set<Bike> bikes =new HashSet<Bike>();

	public Person() {
	}

	public Person(String name) {
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

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	public Set<Bike> getBikes() {
		return bikes;
	}

	public void setBikes(Set<Bike> bikes) {
		this.bikes = bikes;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Person");
		sb.append("{id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
