/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

/**
   general functions that can be used in most any class
**/
public class Functions {

  /**
     Exit with an error message, when an exception occurs.
     @param e the exception
     @param msg the message
  **/
  public static void fail(Exception e, String msg) {
    System.err.println(msg + ": " +  e);
    System.exit(1);
  }
    

  /**
     Convert an array to a string.

  **/
  static public String printArray(Object[] array) {
    if(array == null) {
      return "NULL";
    }
    StringBuffer sb = new StringBuffer();
    sb.append(array.getClass());
    sb.append(" [");
    for(int i=0; i<array.length; i++) {
      if(i>0) {
        sb.append(", ");
      }
      sb.append(array[i]);
    }
    sb.append(" ]");
    return sb.toString();
  }
}
