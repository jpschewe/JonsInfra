/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

import java.beans.PropertyChangeListener;

/**
   generic class that allows objects to have names
**/
public class DefaultNamed extends Bean implements Named {

  /**
     standard constructor
  **/
  public DefaultNamed(String name) {
    super();
    _name = name;
  }
  
  /** the name of the object **/
  private String _name = null;

  /**
     set the name for the object.
     @param n the new name
  **/
  public void setName(String n) {
    String old = new String(n);
    _name = n;
    fireNameChange(old, new String(_name));
  }

  /**
     get the name of the object.
     @return the name
  **/
  public String getName() {
    return _name;
  }

  protected void fireNameChange(String old, String clone) {
    firePropertyChange("name", old, clone);
  }

  public void addNameListener(PropertyChangeListener l) {
    addPropertyChangeListener("name", l);
  }

  public void removeNameListener(PropertyChangeListener l) {
    removePropertyChangeListener("name", l);
  }

}
