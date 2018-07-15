
package de.laliluna.relation.ternary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


import javax.persistence.*;


@Entity
@SequenceGenerator(name = "company_seq", sequenceName = "company_id_seq")
public class Company{


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="company_seq")
	private Integer id;

	private String name;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="company_contract", joinColumns={@JoinColumn(name="company_id")})
	@MapKeyJoinColumn(name="workaholic_id")
	private Map<Workaholic,Contract> contracts = new HashMap<Workaholic,Contract>();

	public Company() {

	}

	public Company(String name) {
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

	public Map<Workaholic,Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Map<Workaholic,Contract> contracts) {
		this.contracts = contracts;
	}

	public String toString() {
		return "Company: " + getId() + " Name: " + getName();
	}
}
