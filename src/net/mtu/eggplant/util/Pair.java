/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package net.mtu.eggplant.util;

/**
   class to put multiple objects in one.  
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

  public String toString() {
    return "[Pair one:" + getOne() + " two: " + getTwo() + "]";
  }

  /**
     Equality is defined by the equality of the objects in the Pair.
  **/
  public boolean equals(final Object o) {
    if(o instanceof Pair) {
      final Pair other = (Pair)o;
      return ( (other.getOne() == null && getOne() == null)
               || (other.getOne() != null && getOne() != null && other.getOne().equals(getOne())) )
        && ( (other.getTwo() == null && getTwo() == null)
             || (other.getTwo() != null && getTwo() != null && other.getTwo().equals(getTwo())) );
    }
    return false;
  }

  public int hashCode() {
    if(getOne() == null) {
      return -1;
    } else {
      return getOne().hashCode();
    }
  }
  
}
