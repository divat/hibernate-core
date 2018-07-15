
package de.laliluna.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author hennebrueder
 * This servlet listener can be configured in your web.xml. It calls the InitSessionFactory to initialize the
 * hibernate configuration on startup and closes it when undeployed.
 * <web-app ..... snip ........
  <listener>
  	<listener-class>de.laliluna.hibernate.ServletListener</listener-class>
  </listener>
</web-app>
 */
@WebListener
public class ServletListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		/**
		 * initialize the factory
		 */
		InitSessionFactory.getInstance();

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		/**
		 * close down sessionFactory and free ressources
		 */
		InitSessionFactory.getInstance().close();

	}

}
