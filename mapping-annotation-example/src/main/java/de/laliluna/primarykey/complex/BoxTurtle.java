
package de.laliluna.primarykey.complex;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author hennebrueder
 */
@Entity
public class BoxTurtle {

	@EmbeddedId
	private BoxTurtleId id;

	private String name;

	public BoxTurtle() {
		super();
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}", new Object[] {
				getClass().getSimpleName(), id, name });
	}

	public BoxTurtleId getId() {
		return id;
	}

	public void setId(BoxTurtleId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
