/*
 * Copyright (c) 2000
 *      Jon Schewe.  All rights reserved
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * I'd appreciate comments/suggestions on the code jpschewe@mtu.net
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
