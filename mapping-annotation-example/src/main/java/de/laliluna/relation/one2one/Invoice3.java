package de.laliluna.relation.one2one;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Invoice3 {
	/**
	 * 
	 */


	@Id
	@GeneratedValue(generator = "foreign_id")
	@GenericGenerator(name = "foreign_id", strategy = "foreign", parameters = { @Parameter(name = "property", value = "order") })
	private Integer id;

	private String number;

	@OneToOne(cascade = CascadeType.ALL,optional=false)
	@PrimaryKeyJoinColumn
	private Order3 order;

	public Invoice3() {

	}

	public Invoice3(Integer id, String number) {
		this.id = id;
		this.number = number;
	}

	public Order3 getOrder() {
		return order;
	}

	public void setOrder(Order3 order) {
		this.order = order;
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

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, number={2}", new Object[] {
				getClass().getSimpleName(), id, number });
	}

}
