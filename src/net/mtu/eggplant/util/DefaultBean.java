/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
   Basic class to add property change support.  
**/
public class DefaultBean implements Bean {
  public DefaultBean() {
    _propertyListener = new PropertyChangeSupport(this);
  }

  /**
     Creates a default bean with source as the source of all events.
  **/
  public DefaultBean(final Object source) {
    _propertyListener = new PropertyChangeSupport(source);
  }
  
  public void addPropertyListener(final PropertyChangeListener listener) {
    _propertyListener.addPropertyChangeListener(listener);
  }

  public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
    _propertyListener.addPropertyChangeListener(propertyName, listener);
  }
  
  public void removePropertyListener(final PropertyChangeListener listener) {
    _propertyListener.removePropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
    _propertyListener.removePropertyChangeListener(propertyName, listener);
  }
  
  protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
    _propertyListener.firePropertyChange(propertyName, oldValue, newValue);
  }

  private PropertyChangeSupport _propertyListener;
}
