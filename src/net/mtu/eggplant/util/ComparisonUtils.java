/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package net.mtu.eggplant.util;

/**
   Handy comparision routines.
**/
final public class ComparisonUtils {

  private ComparisonUtils() {}


  /**
     true is always greater than false.
  **/
  static public int compareBooleans(final boolean one, final boolean two) {
    if(one == two) {
      return 0;
    } else if(one) {
      return -1;
    } else {
      return 1;
    }
  }
  
  /**
     Compare two non-floating point numbers.
  **/
  static public int compareIntegers(final long one, final long two) {
    if(one == two) {
      return 0;
    } else if(one < two) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
     Compare two doubles.  Comparisions exist for double and float because
     casting a float to a double and vice versa can cause loss of precision.
  **/
  static public int compareDoubles(final double one, final double two) {
    if(one == two) {
      return 0;
    } else if(one < two) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
     Compare two floats.  Comparisions exist for double and float because
     casting a float to a double and vice versa can cause loss of precision.
  **/
  static public int compareFloats(final float one, final float two) {
    if(one == two) {
      return 0;
    } else if(one < two) {
      return -1;
    } else {
      return 1;
    }
  }
  
  /**
     Compare two Strings, null is allowed and is greater than every non-null String.
  **/
  static public int compareStrings(final String one, final String two) {
    if(one == null && two != null) {
      return 1;
    } else if(one != null && two == null) {
      return -1;
    } else if(one == null && two == null) {
      return 0;
    } else {
      return one.compareTo(two);
    }
  }

}
