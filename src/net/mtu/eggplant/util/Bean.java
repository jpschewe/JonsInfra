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
   basic class to add property change support.  Remove when we start using
   jdk1.2.x.
**/
public class Bean extends PropertyChangeSupport {
  public Bean() {
    _propertyListener = new PropertyChangeSupport(this);
  }

  public void addPropertyListener(PropertyChangeListener l) {
    _propertyListener.addPropertyChangeListener(l);
  }

  public void removePropertyListener(PropertyChangeListener l) {
    _propertyListener.removePropertyChangeListener(l);
  }

  protected void firePropertyChange(String property, Object old, Object clone) 
  {
    _propertyListener.firePropertyChange(property, old, clone);
  }

  private PropertyChangeSupport _propertyListener;

}
