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
@SequenceGenerator(name = "invoice2_seq", sequenceName = "invoice2_id_seq")
public class Invoice2 {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice2_seq")
	private Integer id;

	private String number;

	@OneToOne(mappedBy="invoice")
	private Order2 order;

	public Invoice2() {

	}

	public Invoice2(Integer id, String number) {
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

	public Order2 getOrder() {
		return order;
	}

	public void setOrder(Order2 order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, number={2}", new Object[] {
				getClass().getSimpleName(), id, number });
	}

}
