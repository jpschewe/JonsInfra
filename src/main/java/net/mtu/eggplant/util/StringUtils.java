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

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

/**
 * Some handy utilities for working with strings.
 */
public final class StringUtils {

  private StringUtils() {
  }

  /**
   * Replace all instances of <tt>search</tt> in <tt>source</tt> with
   * <tt>replace</tt>.
   *
   * @param source the source string
   * @param search what to search for
   * @param replace what to replace {@code search} with
   * @return new String
   */
  public static String searchAndReplace(final String source,
                                        final String search,
                                        final String replace) {
    final String newString;
    final int index = source.indexOf(search);
    if (index != -1) {
      final String front = source.substring(0, index);
      final String end = source.substring(index
          + search.length());
      newString = new StringBuffer().append(front).append(replace).append(searchAndReplace(end, search, replace))
                                    .toString();
    } else {
      newString = source;
    }

    return newString;
  }

  /**
   * If the string is longer than len, truncate it and append "...".
   *
   * @param name the string
   * @param len the max length for the string
   * @return the string never longer than len characters
   */
  public static @PolyNull String trimString(final @PolyNull String name,
                                            final int len) {
    if (len <= 3) {
      throw new IllegalArgumentException("Length must be longer than 3 otherwise all strings are just '...'");
    }
    if (null == name) {
      return null;
    } else if (name.length() > len) {
      return name.substring(0, len
          - 3)
          + "...";
    } else {
      return name;
    }
  }

}
