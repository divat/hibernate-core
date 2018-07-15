
package de.laliluna.inheritance.mappedsuperclass;

import java.text.MessageFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "mouse_seq", sequenceName = "mouse_id_seq")
public class KitchenMouse extends Mouse {


	
	public enum FavouriteCheese{ GOUDA, EMMENTHALER, TETES_DE_MOINS}
	
	@Enumerated(EnumType.STRING)
	private FavouriteCheese favouriteCheese;
	
	public KitchenMouse() {
		
	}
	public FavouriteCheese getFavouriteCheese() {
		return favouriteCheese;
	}


	public void setFavouriteCheese(FavouriteCheese favouriteCheese) {
		this.favouriteCheese = favouriteCheese;
	}


	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, name={2}, favouriteCheese={3}", new Object[] {
				getClass().getSimpleName(), getId(), getName(), favouriteCheese});
	}
}