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

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import java.io.Serializable;

/**
 * Basic class to add property change support.
 * 
 * @version $Revision: 1.6 $
 */
public class DefaultBean implements Bean, Serializable {
  public DefaultBean() {
    _propertyListener = new PropertyChangeSupport(this);
  }

  /**
     Creates a default bean with source as the source of all events.
  **/
  public DefaultBean(final Object source) {
    _propertyListener = new PropertyChangeSupport(source);
  }
  
  public void addPropertyChangeListener(final PropertyChangeListener listener) {
    _propertyListener.addPropertyChangeListener(listener);
  }

  /**
   * @deprecated Use addPropertyChangeListener
   */
  public void addPropertyListener(final PropertyChangeListener listener) {
    addPropertyChangeListener(listener);
  }
  
  public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
    _propertyListener.addPropertyChangeListener(propertyName, listener);
  }
  
  public void removePropertyChangeListener(final PropertyChangeListener listener) {
    _propertyListener.removePropertyChangeListener(listener);
  }

  /**
   * @deprecated Use removePropertyChangeListener
   */
  public void removePropertyListener(final PropertyChangeListener listener) {
    removePropertyChangeListener(listener);
  }
  
  public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
    _propertyListener.removePropertyChangeListener(propertyName, listener);
  }
  
  protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
    _propertyListener.firePropertyChange(propertyName, oldValue, newValue);
  }

  private PropertyChangeSupport _propertyListener;
}
