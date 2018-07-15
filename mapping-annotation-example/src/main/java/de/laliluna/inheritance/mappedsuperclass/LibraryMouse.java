
package de.laliluna.inheritance.mappedsuperclass;

import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "mouse_seq", sequenceName = "mouse_id_seq")
public class LibraryMouse extends Mouse {


	public enum FavouriteBook {
		IN_LOVE_WITH_DR_CHIWAGO, DESIGN_PATTERN, OTHER
	}

	@Enumerated(EnumType.STRING)
	private FavouriteBook favouriteBook;

	public LibraryMouse() {

	}

	public FavouriteBook getFavouriteBook() {
		return favouriteBook;
	}

	public void setFavouriteBook(FavouriteBook favouriteBook) {
		this.favouriteBook = favouriteBook;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}, favouriteBook={3}",
				new Object[] { getClass().getSimpleName(), getId(), getName(),
						getFavouriteBook() });
	}

}
