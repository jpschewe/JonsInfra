/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package net.mtu.eggplant.util;

import java.beans.PropertyChangeListener;

/**
   Basic interface to add property change support.  
**/
public interface Bean {
  public void addPropertyListener(final PropertyChangeListener listener);

  public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener);
  
  public void removePropertyListener(final PropertyChangeListener listener);

  public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener);
}
