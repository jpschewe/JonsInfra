/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package net.mtu.eggplant.util;

import net.mtu.eggplant.util.predicates.InstanceOf;
import net.mtu.eggplant.util.algorithms.Finding;

import java.util.Collection;

/**
   Handy utilities for working with Collections that Sun didn't provide.
**/
final public class CollectionUtils {
  private CollectionUtils() {}

  /**
     @return true if all of the objects in collection are instances of type

     @pre (collection != null)
     @pre (type != null)
  **/
  static public boolean checkInstanceOf(final Collection collection, final Class type) {
    return Finding.every(collection, new InstanceOf(type));
  }
  
}
