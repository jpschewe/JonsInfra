/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

/**
   class to put multiple objects in one.  I got the idea for this class from
   the JGL class Pair.  I didn't use that one because I kind of like to keep
   my code self contained.
**/
public class Pair extends Object {

  public Pair(Object one, Object two) {
    _one = one;
    _two = two;
  }

  private Object _one = null;
  
  public Object getOne() {
    return _one;
  }

  private Object _two = null;
  
  public Object getTwo() {
    return _two;
  }

}
