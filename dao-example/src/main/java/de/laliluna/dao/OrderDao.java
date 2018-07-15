
package de.laliluna.dao;

import de.laliluna.domain.Order;



public interface OrderDao extends BasicDao<Order> {
	
	public void deliver(Order order);

	
}
