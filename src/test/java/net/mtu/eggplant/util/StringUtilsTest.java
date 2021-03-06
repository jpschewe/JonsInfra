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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for StringUtils.
 */
public class StringUtilsTest {

  /**
   * Test simple search and replace.
   */
  @Test
  public void testSearchAndReplace() {
    final String source = "Fred & Barney & Wilma";
    final String search = "Barney";
    final String replace = "Jon";
    final String expectedResult = "Fred & Jon & Wilma";
    final String result = StringUtils.searchAndReplace(source, search, replace);

    assertEquals(expectedResult, result, "a1");

  }

  /**
   * Make sure that search and replace handles special characters.
   */
  @Test
  public void testSearchAndReplace1() {
    final String source = "($return < 10)";
    final String search = "$return";
    final String replace = "__retVal";
    final String expectedResult = "(__retVal < 10)";
    final String result = StringUtils.searchAndReplace(source, search, replace);

    assertEquals(expectedResult, result, "a1");

  }

  /**
   * Test that search and replace handles backslashes.
   */
  @Test
  public void testSearchAndReplace2() {
    final String source = "(!dateStr.equals(\"\"))";
    final String search = "\"";
    final String replace = "\\\"";
    final String expectedResult = "(!dateStr.equals(\\\"\\\"))";
    final String result = StringUtils.searchAndReplace(source, search, replace);
    assertEquals(expectedResult, result, "a1");
  }

}
