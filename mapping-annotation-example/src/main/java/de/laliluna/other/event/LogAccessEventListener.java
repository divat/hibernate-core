package de.laliluna.other.event;

import de.laliluna.other.diverse.HeadGardener;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;


public class LogAccessEventListener implements LoadEventListener {

  private static Logger log = LoggerFactory.getLogger(LogAccessEventListener.class);


  /**
   * simulate access to a security context loading the current user
   *
   * @return
   */
  private String getUserFromSecurityContext() {
    return "Sebastian";
  }

    @Override
    public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
        if (event.getEntityClassName().equals(HeadGardener.class.getName())) {
            String user = getUserFromSecurityContext();
            log.debug("{}", MessageFormat.format(
                    "Headgardener with id {0} is loaded by {1}.", new Object[]{event.getEntityId(), user}));
        }
    }
}
