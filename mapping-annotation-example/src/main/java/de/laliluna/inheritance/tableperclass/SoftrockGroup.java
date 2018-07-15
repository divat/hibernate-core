
package de.laliluna.inheritance.tableperclass;

import javax.persistence.Entity;

/**
 * @author hennebrueder
 *
 */
@Entity
public class SoftrockGroup extends MusicBand{

	private int destroyedGuitars;

	public int getDestroyedGuitars() {
		return destroyedGuitars;
	}

	public void setDestroyedGuitars(int destroyedGuitars) {
		this.destroyedGuitars = destroyedGuitars;
	}

	@Override
	public String toString() {
		
		return "HardrockGroup: " + getId() + " Name: " + getName()+ " destroyed guitars: "+getDestroyedGuitars();
	}
	
	
}
