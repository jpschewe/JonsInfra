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
public class Bean {
  public Bean() {
    _propertyListener = new PropertyChangeSupport(this);
  }

  public void addPropertyListener(PropertyChangeListener listener) {
    _propertyListener.addPropertyChangeListener(listener);
  }

  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    _propertyListener.addPropertyChangeListener(propertyName, listener);
  }
  
  public void removePropertyListener(PropertyChangeListener listener) {
    _propertyListener.removePropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    _propertyListener.removePropertyChangeListener(propertyName, listener);
  }
  
  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) 
  {
    _propertyListener.firePropertyChange(propertyName, oldValue, newValue);
  }

  private PropertyChangeSupport _propertyListener;
}
