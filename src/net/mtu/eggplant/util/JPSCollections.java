/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package net.mtu.eggplant.util;

import java.util.Vector;
import java.util.Enumeration;

/**
   some handy methods for working with collections of objects
**/
final public class JPSCollections {

  /**
     @param list the list to check
     @param theClass the class to check for
     @return true if all of the elements of list are instances of theClass

     @pre (list != null)
     @pre (theClass != null)
  **/
  static public boolean elementsInstanceOf(final Vector list, final Class theClass) {
    Enumeration iter = list.elements();    
    while(iter.hasMoreElements()) {
      Object o = iter.nextElement();
      if(!theClass.isInstance(o)) {
        return false;
      }
    }

    return true;
  }

}
