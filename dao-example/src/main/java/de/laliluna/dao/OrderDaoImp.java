
package de.laliluna.dao;

import de.laliluna.domain.Order;
import de.laliluna.domain.OrderStatus;
import org.hibernate.SessionFactory;



public class OrderDaoImp extends BasicDaoImp<Order> implements OrderDao {
	public OrderDaoImp( SessionFactory factory ) {
		super(factory, Order.class);
	}
	
	public void deliver(Order order){
		order.setStatus(OrderStatus.DELIVERED);
	}
	
}
