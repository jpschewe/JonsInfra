/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.test;

import org.tcfreenet.schewe.utils.StringUtils;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
   Test class for StringUtils.
**/
public class StringUtilsTest extends TestCase {

  static public void main(final String[] args) {
    //Make sure exceptions are thrown for assertions
    System.setProperty("ASSERT_BEHAVIOR", "EXCEPTION");

    final TestSuite suite = new TestSuite();
    //suite.addTest(new StringUtilsTest("testSearchAndReplace2"));
    suite.addTest(suite());
    TestRunner.run(suite);
    
  }

  public StringUtilsTest(final String name) {
    super(name);
  }

  static public TestSuite suite() {
    return new TestSuite(StringUtilsTest.class);
  }

  public void testSearchAndReplace() {
    final String source = "Fred & Barney & Wilma";
    final String search = "Barney";
    final String replace = "Jon";
    final String expectedResult = "Fred & Jon & Wilma";
    final String result = StringUtils.searchAndReplace(source, search, replace);

    assertEquals("a1", expectedResult, result);

  }

  public void testSearchAndReplace1() {
    final String source = "($return < 10)";
    final String search = "$return";
    final String replace = "__retVal";
    final String expectedResult = "(__retVal < 10)";
    final String result = StringUtils.searchAndReplace(source, search, replace);

    assertEquals("a1", expectedResult, result);

  }  

  public void testSearchAndReplace2() {
    final String source = "(!dateStr.equals(\"\"))";
    final String search = "\"";
    final String replace = "\\\"";
    final String expectedResult = "(!dateStr.equals(\\\"\\\"))";
    final String result = StringUtils.searchAndReplace(source, search, replace);    
    assertEquals("a1", expectedResult, result);
  }
}
