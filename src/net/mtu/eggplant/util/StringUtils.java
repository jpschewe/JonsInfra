/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils;
final public class StringUtils {

  private StringUtils() {}

  /**
     Replace all instances of <tt>search</tt> in <tt>source</tt> with
     <tt>replace</tt>.
     
     @return new String

     @pre (source != null)
     @pre (search != null)
     @pre (replace != null)
  **/
  static public String searchAndReplace(final String source, final String search, final String replace) {
    final int searchLength = search.length();
    String newString = source;
    int index = newString.indexOf(search);
    while(index != -1) {
      final String front = newString.substring(0, index);
      final String end = newString.substring(index+searchLength);
      newString = new StringBuffer().append(front).append(replace).append(end).toString();
      index = newString.indexOf(search, index);
    }

    return newString;
  }

}
