/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package net.mtu.eggplant.util;

/**
   This class represents a column for a table.  It is assumed that all data in 
   a column is of the same java class.  There is also a read only flag for the 
   coulmn.  This can be used to make a column read only and can be used with a 
   flag on a row in the data to determine if a cell is editable.  By default a 
   column is read only.
**/
public class ColumnHeader extends DefaultNamed {

  public ColumnHeader(String name, Class theClass) {
    this(name, theClass, true);
  }
  
  public ColumnHeader(String name, Class theClass, boolean readonly) {
    super(name);
    setClass(theClass);
    setReadOnly(readonly);
  }

  /**
     get the class for this column.
     @return the class
  **/
  public Class getClassName() {
    return _className;
  }

  private Class _className = null;
  /**
     set the class for this column.
     @param theClass the new class
  **/
  public void setClass(Class theClass) {
    _className = theClass;
  }

  /** can we write to this column? **/
  private boolean _readOnly = true;

  /**
     set the read only flag on this column.
     @param readonly the flag
  **/
  public void setReadOnly(boolean readonly) {
    _readOnly = readonly;
  }

  /**
     get the read only flag for this column
     @return the flag
  **/
  public boolean getReadOnly() {
    return _readOnly;
  }

  public String toString() {
    return getName() + " " + getClassName() + " " + getReadOnly();
  }
}
