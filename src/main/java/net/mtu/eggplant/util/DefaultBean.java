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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Basic class to add property change support.
 */
public class DefaultBean implements Bean, Serializable {
  /**
   * Base constructor. Source is {@code this}.
   */
  public DefaultBean() {
    this.propertyListener = new PropertyChangeSupport(this);
  }

  /**
   * Creates a default bean with source as the source of all events.
   *
   * @param source source for all events
   */
  public DefaultBean(final Object source) {
    propertyListener = new PropertyChangeSupport(source);
  }

  @Override
  public void addPropertyChangeListener(final PropertyChangeListener listener) {
    propertyListener.addPropertyChangeListener(listener);
  }

  @Override
  public void addPropertyChangeListener(final String propertyName,
                                        final PropertyChangeListener listener) {
    propertyListener.addPropertyChangeListener(propertyName, listener);
  }

  @Override
  public void removePropertyChangeListener(final PropertyChangeListener listener) {
    propertyListener.removePropertyChangeListener(listener);
  }

  @Override
  public void removePropertyChangeListener(final String propertyName,
                                           final PropertyChangeListener listener) {
    propertyListener.removePropertyChangeListener(propertyName, listener);
  }

  /**
   * @param propertyName property to fire a change for
   * @param oldValue the old value
   * @param newValue the new value
   */
  protected void firePropertyChange(final String propertyName,
                                    final @Nullable Object oldValue,
                                    final @Nullable Object newValue) {
    propertyListener.firePropertyChange(propertyName, oldValue, newValue);
  }

  /**
   * @return {@link PropertyChangeSupport#getPropertyChangeListeners()}
   */
  protected PropertyChangeListener[] getPropertyChangeListeners() {
    return propertyListener.getPropertyChangeListeners();
  }

  /**
   * @return {@link PropertyChangeSupport#getPropertyChangeListeners(String)}
   * @param propertyName
   *          {@link PropertyChangeSupport#getPropertyChangeListeners(String)}
   */
  protected PropertyChangeListener[] getPropertyChangeListeners(final String propertyName) {
    return propertyListener.getPropertyChangeListeners(propertyName);
  }

  /**
   * For access to all of <tt>PropertyChangeSupport</tt>.
   *
   * @return the underlying support class
   */
  protected PropertyChangeSupport getPropertyChangeSupport() {
    return propertyListener;
  }

  private final @NotOnlyInitialized PropertyChangeSupport propertyListener;
}
