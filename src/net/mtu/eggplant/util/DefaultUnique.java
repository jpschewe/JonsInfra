/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package net.mtu.eggplant.util;

/**
   Default implementation of the {@link Unique} interface.  Can be used as a
   delegate.
**/
public class DefaultUnique implements Unique {

  public DefaultUnique() {
    if(_nextUID == Long.MAX_VALUE) {
      throw new RuntimeException("You have run out of Unique IDs!");
    }
    _uid = _nextUID++;
  }
  
  static private long _nextUID = Long.MIN_VALUE;

  private long _uid;
  final public long getUID() {
    return _uid;
  }
  
  public boolean equals(final Object o) {
    if(o instanceof Unique) {
      return ((Unique)o).getUID() == getUID();
    } else {
      return false;
    }
  }
  
}
