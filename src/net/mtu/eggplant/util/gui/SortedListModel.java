/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.gui;

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
