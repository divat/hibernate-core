package de.laliluna.relation.wherecondition;

import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author Sebastian Hennebrueder
 *         Date: 07.03.2010
 */
@Entity
public class Car {
	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private boolean deleted;

	public Car() {
	}

	public Car(String name) {
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Car");
		sb.append("{id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", deleted=").append(deleted);
		sb.append('}');
		return sb.toString();
	}
}
