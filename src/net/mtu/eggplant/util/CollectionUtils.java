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

import net.mtu.eggplant.util.predicates.InstanceOf;
import net.mtu.eggplant.util.algorithms.Finding;

import java.util.Collection;
import java.util.HashMap;

/**
 * Handy utilities for working with Collections that Sun didn't provide.
 * 
 * @version $Revision: 1.5 $
 */
final public class CollectionUtils {
  private CollectionUtils() {}

  /**
     @return true if all of the objects in collection are instances of type

     @pre (collection != null)
     @pre (type != null)
  **/
  static public boolean checkInstanceOf(final Collection collection, final Class type) {
    return Finding.every(collection, new InstanceOf(type));
  }

  /**
   * Create a HashMap of expected size using loadRatio.
   * 
   * @pre (expectedSize >= 0)
   * @pre (loadRatio > 0)
   */
  static public HashMap createHashMap(final int expectedSize,
                                      final float loadRatio) {
    return new HashMap(Math.max(1, (int)Math.ceil((float)expectedSize/loadRatio)), loadRatio);
  }

  /**
   * Defaults loadRatio to 0.75.
   *
   * @see #createHashMap(int, float)
   * 
   * @pre (expectedSize >= 0)
   */
  static public HashMap createHashMap(final int expectedSize) {
    return createHashMap(expectedSize, 0.75F);
  }
  
}
