/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package net.mtu.eggplant.util;
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
    final String newString;
    final int index = source.indexOf(search);
    if(index != -1) {
      final String front = source.substring(0, index);
      final String end = source.substring(index+search.length());
      newString = new StringBuffer().append(front).append(replace).append(searchAndReplace(end, search, replace)).toString();
    } else {
      newString = source;
    }

    return newString;
  }

}
