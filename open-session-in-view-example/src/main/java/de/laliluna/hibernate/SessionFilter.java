
package de.laliluna.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

@WebFilter(servletNames = "controller")
public class SessionFilter implements Filter {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SessionFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		SessionFactory factory = InitSessionFactory.getInstance();

		try {
			factory.getCurrentSession().beginTransaction();
			filterChain.doFilter(request, response);
			factory.getCurrentSession().getTransaction().commit();
		} catch (Exception ex) {

			ex.printStackTrace();
			if (factory.getCurrentSession().getTransaction().isActive())
				try {
					log.debug("Try roling back the Hibernate transaction");
					factory.getCurrentSession().getTransaction().rollback();
				} catch (HibernateException e1) {
					log.error("Error rolling back Hibernate Transaction", e1);
				}
			/*
             * we could do something specific to our application, show a error
             * dialog, etc. Just change the behaviour here.
             */
			throw new ServletException(ex);
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
