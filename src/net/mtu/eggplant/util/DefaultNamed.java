/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package net.mtu.eggplant.util;

import java.beans.PropertyChangeListener;

/**
   generic class that allows objects to have names
**/
public class DefaultNamed extends DefaultBean implements Named {

  /**
     standard constructor
  **/
  public DefaultNamed(final String name) {
    super();
    _name = name;
  }
  
  /** the name of the object **/
  private String _name = null;

  /**
     Set the name for the object.  This is a bound property.
     @param n the new name
  **/
  final public void setName(final String n) {
    final String old = new String(n);
    _name = n;
    fireNameChange(old, new String(_name));
  }

  /**
     get the name of the object.
     @return the name
  **/
  final public String getName() {
    return _name;
  }

  final protected void fireNameChange(String old, String clone) {
    firePropertyChange("name", old, clone);
  }

  public String toString() {
    return "Named: " + getName();
  }
  
}
