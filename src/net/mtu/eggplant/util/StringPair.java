/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

public class StringPair extends Pair {
  public StringPair(String one, String two) {
    super(one, two);
  }

  public String getStringOne() {
    return (String)super.getOne();
  }

  public String getStringTwo() {
    return (String)super.getTwo();
  }

  public String toString() {
    return "[StringPair one:" + getStringOne() + " two: " + getStringTwo() + "]";
  }
}
