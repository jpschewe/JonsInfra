/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.algorithms;

import java.util.Collection;
import java.util.Iterator;

/**
   Ways to apply things to Collections.
**/
final public class Applying {
  private Applying() {}

  /**
     Apply func to each object in collection.

     @param collection the collection
     @param func the function to execute on each object

     @pre (collection != null)
     @pre (func != null)
  **/
  static public void forEach(final Collection collection, final Function func) {
    final Iterator iter = collection.iterator();
    while(iter.hasNext()) {
      final Object o = iter.next();
      func.execute(o);
    }
  }

}
