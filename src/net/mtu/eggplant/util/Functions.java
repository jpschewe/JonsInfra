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
    
  
}
