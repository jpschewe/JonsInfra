/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.event;

import java.util.Collection;
import java.util.EventObject;

public class CollectionEvent extends EventObject {

  public CollectionEvent(final Object source, final Collection data) {
    super(source);
    _data = data;
  }

  private Collection _data;
  
  /**
     Get the Collection of objects that were added/removed.
  **/
  public Collection getData() {
    return _data;
  }

}
