
package de.laliluna.example;


import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
@Entity
@SequenceGenerator(name = "honey_seq", sequenceName = "honey_id_seq")
public class Honey implements Serializable {
	private static final long serialVersionUID = 6789761254603569794L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="honey_seq")
	// for MySQL use GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String taste;
	
	@OneToMany(mappedBy="honey")
	private Set<Bee> bees = new HashSet<Bee>();
	
	public Honey() {
	}

	public Honey(String name, String taste) {
		this.name = name;
		this.taste = taste;
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

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	/**
	 * @return the bees
	 */
	public Set<Bee> getBees() {
		return bees;
	}

	/**
	 * @param bees the bees to set
	 */
	public void setBees(Set<Bee> bees) {
		this.bees = bees;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("Honey: {0} {1} {2}", new Object[]{id, name, taste});
	}

}
