
package de.laliluna.webstock;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity  @Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
	private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String number;

	@OneToMany(mappedBy = "order")
    @Cascade(CascadeType.SAVE_UPDATE)
	private List<OrderLine> orderLines = new java.util.ArrayList<>();

	public Order() {
	}

    public Order(String number) {
        this.number = number;
        date = new Date();
    }

    public List getOrderLines() {
		return orderLines;
	}


	public void setOrderLines(List orderLines) {
		this.orderLines = orderLines;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
		return "Order: "+getId()+" Number: "+getNumber()+" Date: "+getDate();
	}


    public void add(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrder(this);

    }
}
