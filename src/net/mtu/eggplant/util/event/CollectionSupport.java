/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package net.mtu.eggplant.util.event;

import java.util.Collection;

import javax.swing.event.EventListenerList;

/**
   Adds support for Collection events.
**/
public class CollectionSupport {

  /**
     @param source the object that is to be the source of the events

     @pre (source != null)
  **/
  public CollectionSupport(final Object source) {
    _source = source;
    _listeners = new EventListenerList();
  }
  private Object _source;
  
  /**
     @param data the objects that were added
     
     @pre (data != null)
  **/
  public void fireObjectsAdded(final Collection data) {
    final CollectionEvent ce = new CollectionEvent(_source, data);
    final Object[] listeners = _listeners.getListenerList();
    for(int i=listeners.length-2; i>=0; i-=2) {
      if(listeners[i] == CollectionListener.class) {
        ((CollectionListener)listeners[i+1]).objectsAdded(ce);
      }
    }
  }

  /**
     @param data the objects that were removed
     
     @pre (data != null)
  **/
  public void fireObjectsRemoved(final Collection data) {
    final CollectionEvent ce = new CollectionEvent(_source, data);
    final Object[] listeners = _listeners.getListenerList();
    for(int i=listeners.length-2; i>=0; i-=2) {
      if(listeners[i] == CollectionListener.class) {
        ((CollectionListener)listeners[i+1]).objectsRemoved(ce);
      }
    }
  }

  private EventListenerList _listeners;
  /**
     @pre (listener != null)
  **/
  public void addCollectionListener(final CollectionListener listener) {
    _listeners.add(CollectionListener.class, listener);
  }

  /**
     @pre (listener != null)
  **/
  public void removeCollectionListener(final CollectionListener listener) {
    _listeners.remove(CollectionListener.class, listener);
  }

}
