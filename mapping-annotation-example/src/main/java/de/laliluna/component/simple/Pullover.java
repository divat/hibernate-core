
package de.laliluna.component.simple;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Embeddable;

/**
 * @author hennebrueder
 */
@Embeddable
public class Pullover{

	public enum Color {
		RED, BLUE, YELLOW
	}

	private Color color;

	private String person;

	public Pullover() {
		super();
	}

	public Pullover(Color color, String person) {
		super();
		this.color = color;
		this.person = person;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: color={1}, person={2}", new Object[] {
				getClass().getSimpleName(), color, person});
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

}
