/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;

/**
   Class that controls wether debugging is on or not.  Fires ChangeEvents when
   the debug state changes.  These events have a source of <tt>Debug.class</tt>.
**/
final public class Debug {

  private static boolean _debug = false;

  static public void print(boolean b) {
    if(_debug) {
      internalPrint(new Boolean(b).toString());
    }
  }

  static public void print(char c) {
    if(_debug) {
      internalPrint(new Character(c).toString());
    }
  }

  static public void print(char[] s) {
    if(_debug) {
      internalPrint(s == null ? "NULL" : s.toString());
    }
  }

  static public void print(double d) {
    if(_debug) {
      internalPrint(new Double(d).toString());
    }
  }

  static public void print(float f) {
    if(_debug) {
      internalPrint(new Float(f).toString());
    }
  }

  static public void print(int i) {
    if(_debug) {
      internalPrint(new Integer(i).toString());
    }
  }

  static public void print(long l) {
    if(_debug) {
      internalPrint(new Long(l).toString());
    }
  }

  static public void print(Object obj) {
    if(_debug) {
      internalPrint(obj == null ? "NULL" : obj.toString());
    }
  }

  static public void print(String s) {
    if(_debug) {
      internalPrint(s);
    }
  }

  static public void println(boolean b) {
    if(_debug) {
      internalPrint(new Boolean(b).toString() + "\n");
    }
  }

  static public void println(char c) {
    if(_debug) {
      internalPrint(new Character(c).toString() + "\n");
    }
  }

  static public void println(char[] s) {
    if(_debug) {
      internalPrint((s == null ? "NULL" : s.toString()) + "\n");
    }
  }

  static public void println(double d) {
    if(_debug) {
      internalPrint(new Double(d).toString() + "\n");
    }
  }

  static public void println(float f) {
    if(_debug) {
      internalPrint(new Float(f).toString() + "\n");
    }
  }

  static public void println(int i) {
    if(_debug) {
      internalPrint(new Integer(i).toString() + "\n");
    }
  }

  static public void println(long l) {
    if(_debug) {
      internalPrint(new Long(l).toString() + "\n");
    }
  }

  static public void println(Object obj) {
    if(_debug) {
      internalPrint((obj == null ? "NULL" : obj.toString()) + "\n");
    }
  }

  static public void println(String s) {
    if(_debug) {
      internalPrint(s + "\n");
    }
  }  

  static public void errPrint(boolean b) {
    if(_debug) {
      internalErrPrint(new Boolean(b).toString());
    }
  }

  static public void errPrint(char c) {
    if(_debug) {
      internalErrPrint(new Character(c).toString());
    }
  }

  static public void errPrint(char[] s) {
    if(_debug) {
      internalErrPrint(s == null ? "NULL" : s.toString());
    }
  }

  static public void errPrint(double d) {
    if(_debug) {
      internalErrPrint(new Double(d).toString());
    }
  }

  static public void errPrint(float f) {
    if(_debug) {
      internalErrPrint(new Float(f).toString());
    }
  }

  static public void errPrint(int i) {
    if(_debug) {
      internalErrPrint(new Integer(i).toString());
    }
  }

  static public void errPrint(long l) {
    if(_debug) {
      internalErrPrint(new Long(l).toString());
    }
  }

  static public void errPrint(Object obj) {
    if(_debug) {
      internalErrPrint(obj == null ? "NULL" : obj.toString());
    }
  }

  static public void errPrint(String s) {
    if(_debug) {
      internalErrPrint(s);
    }
  }

  static public void errPrintln(boolean b) {
    if(_debug) {
      internalErrPrint(new Boolean(b).toString() + "\n");
    }
  }

  static public void errPrintln(char c) {
    if(_debug) {
      internalErrPrint(new Character(c).toString() + "\n");
    }
  }

  static public void errPrintln(char[] s) {
    if(_debug) {
      internalErrPrint((s == null ? "NULL" : s.toString()) + "\n");
    }
  }

  static public void errPrintln(double d) {
    if(_debug) {
      internalErrPrint(new Double(d).toString() + "\n");
    }
  }

  static public void errPrintln(float f) {
    if(_debug) {
      internalErrPrint(new Float(f).toString() + "\n");
    }
  }

  static public void errPrintln(int i) {
    if(_debug) {
      internalErrPrint(new Integer(i).toString() + "\n");
    }
  }

  static public void errPrintln(long l) {
    if(_debug) {
      internalErrPrint(new Long(l).toString() + "\n");
    }
  }

  static public void errPrintln(Object obj) {
    if(_debug) {
      internalErrPrint((obj == null ? "NULL" : obj.toString()) + "\n");
    }
  }

  static public void errPrintln(String s) {
    if(_debug) {
      internalErrPrint(s + "\n");
    }
  }  
  
  static private void internalPrint(String s) {
    System.out.print(s);
    // print to some cool panel here too later
  }

  static private void internalErrPrint(String s) {
    System.err.print(s);
    // print to some cool panel here too later
  }

  static public boolean isDebugMode() { return _debug; }
  /**
     Change the debug mode.  Fires a ChangeEvent.
  **/
  static public void setDebugMode(boolean debug) {
    _debug = debug;
    fireDebugStateChanged();
  }

  /**
     Add a listener for the debug mode changing.
  **/
  static public void addDebugStateListener(final ChangeListener listener) {
    _listenerList.add(ChangeListener.class, listener);
  }

  /**
     Remove a listener for the debug mode changing.
  **/
  static public void removeDebugStateListener(final ChangeListener listener) {
    _listenerList.add(ChangeListener.class, listener);
  }

  static private void fireDebugStateChanged() {
    final Object[] listeners = _listenerList.getListenerList();
    for(int i = listeners.length-2; i>=0; i-=2) {
      ((ChangeListener)listeners[i+1]).stateChanged(_changeEvent);
    }
  }

  /**
     List of listeners.
  **/
  final static private EventListenerList _listenerList = new EventListenerList();
  /**
     Single change event we use.
  **/
  final static private ChangeEvent _changeEvent = new ChangeEvent(Debug.class);
  
}
