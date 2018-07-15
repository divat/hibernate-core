package de.laliluna.other.interceptor;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;


import java.io.Serializable;
import java.util.Date;

import de.laliluna.other.diverse.HeadGardener;


public class HistoryLogInterceptor extends EmptyInterceptor {

  private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HistoryLogInterceptor.class);

  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
  log.debug("onFlushDirty interceptor");
    boolean hasChanged = false;
    if (entity instanceof HeadGardener) {
      /* Hibernate expects true, if we changed anything */
      hasChanged = true;

      /* find properties and set last change user and timestamp */
      for (int i = 0; i < propertyNames.length; i++) {
        String propertyName = propertyNames[i];
        if (propertyName.equals("lastChangeUser"))
          currentState[i] = getCurrentUser();
        else if (propertyName.equals("lastChangeDate"))
          currentState[i] = new Date();

      }
    }

    return hasChanged;
  }

  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    log.debug("onSave interceptor");
    boolean hasChanged = false;
    if (entity instanceof HeadGardener) {
      hasChanged = true;

      /* find properties and set last change user and timestamp */
      for (int i = 0; i < propertyNames.length; i++) {
        String propertyName = propertyNames[i];
        if (propertyName.equals("lastChangeUser"))
          state[i] = getCurrentUser();
        else if (propertyName.equals("lastChangeDate"))
          state[i] = new Date();

      }
    }

    return hasChanged;
  }


  /**
   * simulates fetching currentUser from securityContext
   *
   * @return
   */
  private String getCurrentUser() {
    return "Sebastian";
  }
}
