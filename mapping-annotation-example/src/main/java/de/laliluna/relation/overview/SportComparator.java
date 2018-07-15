package de.laliluna.relation.overview;

import java.util.Comparator;

public class SportComparator implements Comparator<Sport> {

	public int compare(Sport o1, Sport o2) {
		if (o1 == null || o1.getName()== null)
			return 1;
		if (o2 == null || o2.getName()== null)
			return -1;
		return o1.getName().compareTo(o2.getName());
	}
	
	
}
