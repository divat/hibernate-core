package de.laliluna.relation.wherecondition;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Sebastian Hennebrueder
 *         Date: 07.03.2010
 */
@Entity
public class Bike {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	public Bike() {
	}

	public Bike(String name) {
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
		final StringBuilder sb = new StringBuilder();
		sb.append("Bike");
		sb.append("{id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
