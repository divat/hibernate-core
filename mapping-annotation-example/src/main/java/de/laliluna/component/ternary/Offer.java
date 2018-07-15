package de.laliluna.component.ternary;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "offer_seq", sequenceName = "offer_id_seq")
public class Offer {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_seq")
	private Integer id;

	@ElementCollection
	@CollectionTable(name = "offer_lines", joinColumns = @JoinColumn(name = "offer_id"))
	private Set<OfferLine> offerLines = new HashSet<OfferLine>();

	public Offer() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<OfferLine> getOfferLines() {
		return offerLines;
	}

	public void setOfferLines(Set<OfferLine> offerLines) {
		this.offerLines = offerLines;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Offer: " + id);
		for (Iterator iter = offerLines.iterator(); iter.hasNext();) {
			OfferLine element = (OfferLine) iter.next();
			buffer.append("\n  " + element.toString());
		}

		return buffer.toString();

	}

}
