/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.algorithms;

import org.tcfreenet.schewe.utils.Function;
  
import java.util.Collection;

/**
   Some copy algorithms for Collections.
**/
final public class Copying {

  private Copying() {}

  /**
     Copy all elements from <tt>source</tt> to <tt>dest</tt>

     @pre (source != null)
     @pre (dest != null)
  **/
  static public void copy(final Collection source, final Collection dest) {
    Applying.forEach(source, new Function() {
      public void execute(final Object o) {
        dest.add(o);
      }
    });
  }
}
