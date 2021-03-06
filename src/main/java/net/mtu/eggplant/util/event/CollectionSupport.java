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
package net.mtu.eggplant.util.event;

import java.util.Collection;
import java.util.function.BiConsumer;

import javax.swing.event.EventListenerList;

/**
 * Adds support for Collection events.
 */
public class CollectionSupport {

  /**
   * @param source the object that is to be the source of the events
   */
  public CollectionSupport(final Object source) {
    this.source = source;
    this.listeners = new EventListenerList();
  }

  private final Object source;

  /**
   * @param data the objects that were added
   */
  public void fireObjectsAdded(final Collection<?> data) {
    fireObjectsEvent(data, (listener,
                            event) -> listener.objectsAdded(event));

  }

  /**
   * @param data the objects that were removed
   */
  public void fireObjectsRemoved(final Collection<?> data) {
    fireObjectsEvent(data, (listener,
                            event) -> listener.objectsRemoved(event));
  }

  private void fireObjectsEvent(final Collection<?> data,
                                final BiConsumer<CollectionListener, CollectionEvent> eventCallback) {
    final CollectionEvent ce = new CollectionEvent(source, data);
    final Object[] listeners = this.listeners.getListenerList();
    for (int i = listeners.length
        - 2; i >= 0; i -= 2) {
      if (listeners[i] == CollectionListener.class) {
        eventCallback.accept(((CollectionListener) listeners[i
            + 1]), ce);
      }
    }
  }

  private final EventListenerList listeners;

  /**
   * @param listener the new listener
   */
  public void addCollectionListener(final CollectionListener listener) {
    listeners.add(CollectionListener.class, listener);
  }

  /**
   * @param listener the listener to remove
   */
  public void removeCollectionListener(final CollectionListener listener) {
    listeners.remove(CollectionListener.class, listener);
  }

}
