/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.algorithms;

import org.tcfreenet.schewe.utils.UnaryPredicate;

import java.util.Collection;
import java.util.Iterator;

/**
   Ways to find things in collections.
**/
final public class Finding {

  private Finding() {}

  /**
     @return true if every element in collection causes predicate to return
     true.  An empty collection returns true.
  **/
  static public boolean every(final Collection collection, final UnaryPredicate predicate) {
    final Iterator iter = collection.iterator();
    boolean retval = true;
    while(iter.hasNext() && retval) {
      final Object o = iter.next();
      retval = predicate.execute(o);
    }
    return retval;
  }

  /**
     @return true if some element in collection causes predicate to return
     true.  An empty collection returns false.
  **/
  static public boolean some(final Collection collection, final UnaryPredicate predicate) {
    final Iterator iter = collection.iterator();
    boolean retval = false;
    while(iter.hasNext() && !retval) {
      final Object o = iter.next();
      retval = predicate.execute(o);
    }
    return retval;
  }
  
}
