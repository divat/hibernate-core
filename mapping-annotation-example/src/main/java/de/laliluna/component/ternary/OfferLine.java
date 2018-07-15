package de.laliluna.component.ternary;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Parent;
@Embeddable
public class OfferLine {



	private int quantity;

	@OneToOne
	@JoinColumn(name="offeritem_id")
	private OfferItem offerItem;

	@Parent
	private Offer offer;

	public OfferLine() {

	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public OfferItem getOfferItem() {
		return offerItem;
	}

	public void setOfferItem(OfferItem offerItem) {
		this.offerItem = offerItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OfferLine quant.: " + quantity + " Item: "
				+ offerItem.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof OfferLine)) // if obj == null then this is
			// also false
			return false;
		if (obj == this)
			return true;
		OfferLine compareTarget = (OfferLine) obj;
		if (compareTarget.offer != null && offer != null) {
			if (!compareTarget.offer.equals(offer))
				return false;
		} else if (compareTarget.offer == null && offer != null)
			return false;
		else if (compareTarget.offer != null && offer == null)
			return false;
		if (compareTarget.offerItem != null && offerItem != null) {
			if (!compareTarget.offerItem.equals(offerItem))
				return false;
		} else if (compareTarget.offerItem == null && offerItem != null)
			return false;
		else if (compareTarget.offerItem != null && offerItem == null)
			return false;
		if (compareTarget.quantity != quantity)
			return false;

		return true;
	}

	@Override
	public int hashCode() {

		int result = 17;
		result = result * 37 + quantity;
		if (offerItem != null)
			result = result * 37 + offerItem.hashCode();
		if (offer != null)
			result = result * 37 + offer.hashCode();
		return result;
	}

}
