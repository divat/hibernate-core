
package de.laliluna.primarykey.complex;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author hennebrueder
 */
@Entity
@IdClass(SpottedTurtleId.class)
public class SpottedTurtle {

	@Id
	private String location;

	@Id
	private String favouriteSalad;

	private String name;

	public SpottedTurtle() {
		super();
	}

	public SpottedTurtle(String location, String favoriteSalad, String name) {
		super();
		this.location = location;
		this.favouriteSalad = favoriteSalad;
		this.name = name;
	}

	@Override
	public String toString() {
		return MessageFormat.format(
				"{0} location={1}, favoriteSalad={2}, name={3}", new Object[] {
						getClass().getSimpleName(), location, favouriteSalad,
						name });
	}

	public String getFavouriteSalad() {
		return favouriteSalad;
	}

	public void setFavouriteSalad(String favoriteSalad) {
		this.favouriteSalad = favoriteSalad;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
