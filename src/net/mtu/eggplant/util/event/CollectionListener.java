/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.event;

import java.util.EventListener;

/**
   Listener for events fired on collections that change.
**/
public interface CollectionListener extends EventListener {

  /**
     Called to notify the listener that objects have been added to the
     Collection.
  **/
  public void objectsAdded(final CollectionEvent ce);

  /**
     Called to notify the listener that objects have been removed from the
     Collection.
  **/
  public void objectsRemoved(final CollectionEvent ce);

}
