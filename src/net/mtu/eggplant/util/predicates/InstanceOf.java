/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.predicates;

import org.tcfreenet.schewe.utils.UnaryPredicate;

/**
   return true if object is an instanceof type.
**/
final public class InstanceOf implements UnaryPredicate {

  /**
     @pre (type != null)
  **/
  public InstanceOf(final Class type) {
    _type = type;
  }
  private Class _type;

  public boolean execute(final Object o) {
    return _type.isInstance(o);
  }
  

}
