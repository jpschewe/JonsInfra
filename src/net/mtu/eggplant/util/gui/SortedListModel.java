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
package net.mtu.eggplant.util.gui;

import javax.swing.AbstractListModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;

/**
   A ListModel that orders the objects according to a comparator and does
   sorted insert.  The comparator must be able to handle all objects in the
   list.
**/
public class SortedListModel extends AbstractListModel {

  /**
     Create a SortedListModel with comparator.

     @pre (comparator != null)
  **/
  public SortedListModel(final Comparator comparator) {
    this(comparator, new ArrayList(10));
  }

  /**
     Create a SortedListModel with comparator and initalize with the list of
     objects in collection.
     
     @pre (comparator != null)
     @pre (collection != null)
  **/
  public SortedListModel(final Comparator comparator, final Collection collection) {
    _list = new ArrayList(collection);
    _comparator = comparator;
    Collections.sort(_list, _comparator);
  }
  
  private Comparator _comparator;
  private List _list;

  //ListModel
  public int getSize() {
    return _list.size();
  }

  public Object getElementAt(final int index) {
    return _list.get(index);
  }
  //end ListModel
  
  /**
     Add o to the list.  Sorted insert.
  **/
  public void add(final Object o) {
    final Iterator iter = _list.iterator();
    int index = 0;
    boolean flag = false;
    while(iter.hasNext() && !flag) {
      final Object listElement = iter.next();
      if(_comparator.compare(o, listElement) > 0) {
        flag = true;
        _list.add(index, o);
      }
      index++;
    }
    if(!flag) {
      _list.add(index, o);
    }
    fireIntervalAdded(this, index, index);
  }

  /**
     Remove the first occurrance of o from the list.
  **/
  public void remove(final Object o) {
    final int index = _list.indexOf(o);
    if(_list.remove(o)) {
      fireIntervalRemoved(this, index, index);
    }
  }
  
}
