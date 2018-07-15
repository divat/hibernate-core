package de.laliluna.relation.one2one;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "order2_seq", sequenceName = "order2_id_seq")
public class Order2 {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order2_seq")
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_id")
	private Invoice2 invoice;

	private String number;

	public Order2() {

	}

	public Order2(Integer id, String number) {
		this.id = id;
		this.number = number;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Invoice2 getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice2 invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, number={2}", new Object[] {
				getClass().getSimpleName(), id, number });
	}

}
