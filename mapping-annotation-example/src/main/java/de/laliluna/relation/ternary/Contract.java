
package de.laliluna.relation.ternary;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "contract_seq", sequenceName = "contract_id_seq")
public class Contract{


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="contract_seq")
	private Integer id;

	private String name;

	public Contract() {

	}

	public Contract(String name) {
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

	public String toString() {
		return "Contract: " + getId() + " Name: " + getName();
	}
}
