/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

/**
   This interface is used to represent the uniqueness of an object.  Each
   object within a JVM that implements the Unique interface must return a
   different uid unless those objects are to be considered equal.  An easy way
   to implement this is to create a new {@link DefaultUnique} object in the
   constructor and delegate the getUID() method to that object.
**/
public interface Unique {
  public long getUID();
}
