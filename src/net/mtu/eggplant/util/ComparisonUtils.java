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
 * Handy comparision routines.
 * 
 * @version $Revision$
 */
public final class ComparisonUtils {

  private ComparisonUtils() {
  }

  /**
   * true is always greater than false.
   **/
  public static int compareBooleans(final boolean one,
                                    final boolean two) {
    if (one == two) {
      return 0;
    } else if (one) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * Compare two non-floating point numbers.
   **/
  public static int compareIntegers(final long one,
                                    final long two) {
    if (one == two) {
      return 0;
    } else if (one < two) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * Compare two doubles. Comparisions exist for double and float because
   * casting a float to a double and vice versa can cause loss of precision.
   **/
  public static int compareDoubles(final double one,
                                   final double two) {
    if (one == two) {
      return 0;
    } else if (one < two) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * Compare two floats. Comparisions exist for double and float because casting
   * a float to a double and vice versa can cause loss of precision.
   **/
  public static int compareFloats(final float one,
                                  final float two) {
    if (one == two) {
      return 0;
    } else if (one < two) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * Compare two Strings, null is allowed and is greater than every non-null
   * String.
   **/
  public static int compareStrings(final String one,
                                   final String two) {
    if (one == null && two != null) {
      return 1;
    } else if (one != null && two == null) {
      return -1;
    } else if (one == null && two == null) {
      return 0;
    } else {
      return one.compareTo(two);
    }
  }

}
