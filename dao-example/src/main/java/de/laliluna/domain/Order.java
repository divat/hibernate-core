
package de.laliluna.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Order {
    @Id @GeneratedValue
	private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String number;

    @Enumerated
	private OrderStatus status;
	
    @OneToMany(mappedBy = "order")
	private List<OrderLine> orderLines = new java.util.ArrayList<>();

	public Order() {
		super();
	}


	public List<OrderLine> getOrderLines() {
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
	
	public String toString() {
		return "Order: "+getId()+" Number: "+getNumber()+" Date: "+getDate();
	}

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
