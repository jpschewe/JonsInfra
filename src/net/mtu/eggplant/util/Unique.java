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

import java.util.Comparator;

/**
   This interface is used to represent the uniqueness of an object.  Each
   object within a JVM that implements the Unique interface must return a
   different uid unless those objects are to be considered equal.  An easy way
   to implement this is to create a new {@link DefaultUnique} object in the
   constructor and delegate the getUID() method to that object.
**/
public interface Unique {
  public long getUID();

  /**
     Comparator for comparing unique objects.
  **/
  final static public Comparator UNIQUE_COMPARATOR = new UComparator();

  /**
     This is here just because javadoc and javac don't agree on what is valid
     java code.
  **/
  final static /*package*/ class UComparator implements Comparator {
    private UComparator() {}
    public boolean equals(final Object o) {
      return o == this;
    }
    
    /**
       @throws ClassCastException if o1 and o2 are not instances of Unique
    **/
    public int compare(final Object o1, final Object o2) throws ClassCastException {
      if(o1.equals(o2)) {
        return 0;
      } else {
        final Unique u1 = (Unique)o1;
        final Unique u2 = (Unique)o2;
        if(u1.getUID() < u2.getUID()) {
          return -1;
        } else {
          return 1;
        }
      }
    }
  };
  
}
