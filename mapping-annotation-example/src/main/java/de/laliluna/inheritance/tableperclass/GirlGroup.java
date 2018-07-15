
package de.laliluna.inheritance.tableperclass;

import javax.persistence.Entity;

/**
 * @author hennebrueder
 *
 */
@Entity
public class GirlGroup extends MusicBand {

	private boolean cryingGroupies;
	
	public boolean isCryingGroupies() {
		return cryingGroupies;
	}

	public void setCryingGroupies(boolean cryingGroupies) {
		this.cryingGroupies = cryingGroupies;
	}

	@Override
	public String toString() {
		return "BoyGroup: " + getId() + " Name: " + getName()+ " crying groupies: "+isCryingGroupies();
	}
	
	
}
