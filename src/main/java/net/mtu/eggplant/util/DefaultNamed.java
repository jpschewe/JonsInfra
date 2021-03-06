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
 * Generic class that allows objects to have names and listeners.
 */
public class DefaultNamed extends DefaultBean implements Named {

  /**
   * @param name {@link #getName()}
   **/
  public DefaultNamed(final String name) {
    super();
    this.name = name;
  }

  /** the name of the object **/
  private String name;

  /**
   * Set the name for the object. This is a bound property.
   *
   * @param n the new name
   **/
  public final void setName(final String n) {
    final String old = n;
    name = n;
    fireNameChange(old, name);
  }

  /**
   * get the name of the object.
   *
   * @return the name
   **/
  @Override
  public final String getName() {
    return name;
  }

  /**
   * @param old old name
   * @param newValue new name
   */
  protected final void fireNameChange(final String old,
                                      final String newValue) {
    firePropertyChange("name", old, newValue);
  }

  @Override
  public String toString() {
    return "Named: "
        + getName();
  }

}
