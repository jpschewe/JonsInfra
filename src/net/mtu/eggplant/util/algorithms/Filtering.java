/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package net.mtu.eggplant.util.algorithms;

import net.mtu.eggplant.util.Function;
import net.mtu.eggplant.util.UnaryPredicate;

import java.util.Collection;

/**
   Utility functions for filtering collections.
**/
final public class Filtering {

  private Filtering() {}

  /**
     Select all objects from source that return true for predicate and put them in dest.

     @param source the source collection
     @param dest the destination collection, usually empty
     @param predicate returns true for each object to select
     @throws UnsupportedOperationException if dest is an unmodifiable container
     
     @pre (source != null)
     @pre (dest != null)
     @pre (predicate != null)
  **/
  static public void select(final Collection source, final Collection dest, final UnaryPredicate predicate) throws UnsupportedOperationException {
    Applying.forEach(source, new Function() {
      public void execute(final Object o) {
        if(predicate.execute(o)) {
          dest.add(o);
        }
      }
    });
  }

}
