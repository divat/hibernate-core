
package de.laliluna.primarykey.complex;

import java.io.Serializable;
import java.text.MessageFormat;


public class SpottedTurtleId implements Serializable{
    private String location;

    private String favouriteSalad;

    public SpottedTurtleId() {
	super();
    }

    public SpottedTurtleId(String location, String favoriteSalad) {
	super();
	this.location = location;
	this.favouriteSalad = favoriteSalad;
    }

    @Override
    public String toString() {
    	return MessageFormat.format(
				"{0} location={1}, favoriteSalad={2}", new Object[] {
						getClass().getSimpleName(), location, favouriteSalad });
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

}
