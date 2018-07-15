package de.laliluna.webstock.web;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;
import de.laliluna.webstock.Article;
import de.laliluna.webstock.Order;
import de.laliluna.webstock.OrderLine;



public class ListAllOrders implements BasicAction {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		// create some dummy data
		Article a1 = new Article("blue pencil",5.55f);
		Article a2 = new Article("red pencil",3.55f);

		Order order = new Order(""+ (new Random()).nextInt(1000));
		order.add(new OrderLine(5, a1));
		order.add(new OrderLine(new Random().nextInt(50), a2));

		session.save(a1);
		session.save(a2);
		session.save(order);
		
		
		
	
		List list = session.createQuery("select o from Order o").list();
		request.setAttribute("orders", list);
		return "orderlist.jsp";

	}

}
