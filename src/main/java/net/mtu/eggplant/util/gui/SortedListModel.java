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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A ListModel that orders the objects according to a comparator and does sorted
 * insert. The comparator must be able to handle all objects in the list.
 *
 * @param <E> type of elements in the table model
 */
public class SortedListModel<@NonNull E> extends AbstractListModel<@NonNull E> {

  /**
   * Create a SortedListModel with comparator.
   *
   * @param comparator the comparator to use
   */
  public SortedListModel(final Comparator<@NonNull E> comparator) {
    this(comparator, new ArrayList<@NonNull E>(10));
  }

  /**
   * Create a SortedListModel with comparator and initalize with the list of
   * objects in collection.
   *
   * @param comparator the comparator to use
   * @param collection the objects to put in the model
   **/
  public SortedListModel(final Comparator<@NonNull E> comparator,
                         final Collection<@NonNull E> collection) {
    this.list = new ArrayList<>(collection);
    this.comparator = comparator;
    Collections.sort(this.list, this.comparator);
  }

  private Comparator<@NonNull E> comparator;

  private List<@NonNull E> list;

  // ListModel
  @Override
  public int getSize() {
    return list.size();
  }

  @Override
  public @NonNull E getElementAt(final int index) {
    return list.get(index);
  }
  // end ListModel

  /**
   * Add o to the list. Sorted insert.
   *
   * @param o the object to add to the model
   **/
  public void add(final @NonNull E o) {
    final Iterator<@NonNull E> iter = list.iterator();
    int index = 0;
    boolean flag = false;
    while (iter.hasNext()
        && !flag) {
      final E listElement = iter.next();
      if (comparator.compare(o, listElement) > 0) {
        flag = true;
        list.add(index, o);
      }
      index++;
    }
    if (!flag) {
      list.add(index, o);
    }
    fireIntervalAdded(this, index, index);
  }

  /**
   * Remove the first occurrance of o from the list.
   *
   * @param o the object to remove from the model
   **/
  public void remove(final @NonNull E o) {
    final int index = list.indexOf(o);
    if (list.remove(o)) {
      fireIntervalRemoved(this, index, index);
    }
  }

}
