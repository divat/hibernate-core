package de.laliluna.relation.one2one;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "order3_seq", sequenceName = "order3_id_seq")
public class Order3 {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order3_seq")
	private Integer id;

	private String number;

	public Order3() {

	}

	public Order3(Integer id, String number) {
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

	@Override
	public String toString() {
		return MessageFormat.format("{0}: id={1}, number={2}", new Object[] {
				getClass().getSimpleName(), id, number });
	}

}
