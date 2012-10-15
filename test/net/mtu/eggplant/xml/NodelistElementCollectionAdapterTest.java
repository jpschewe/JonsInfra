/*
 * Copyright (c) 2008
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
package net.mtu.eggplant.xml;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Some tests for {@link NodelistElementCollectionAdapter}.
 */
public class NodelistElementCollectionAdapterTest {

  @Test
  public void testEmptyNodelist() {
    final Document doc = XMLUtils.DOCUMENT_BUILDER.newDocument();    
    final NodeList nodelist = doc.getChildNodes();
    final NodelistElementCollectionAdapter adapter = new NodelistElementCollectionAdapter(nodelist);
    Assert.assertFalse(adapter.hasNext());
  }
  
  @Test
  public void testNext() {
    final Document doc = XMLUtils.DOCUMENT_BUILDER.newDocument();
    final Element top = doc.createElementNS(null, "top");
    doc.appendChild(top);
    
    final Element expectedFirst = doc.createElementNS(null, "foo");
    top.appendChild(expectedFirst);
    final Element expectedSecond = doc.createElementNS(null, "foo");
    top.appendChild(expectedSecond);
    final NodeList nodelist = top.getChildNodes();
    final NodelistElementCollectionAdapter adapter = new NodelistElementCollectionAdapter(nodelist);
    
    Assert.assertTrue(adapter.hasNext());
    final Element actualFirst = adapter.next();
    Assert.assertEquals(expectedFirst, actualFirst);
    
    Assert.assertTrue(adapter.hasNext());
    final Element actualSecond = adapter.next();
    Assert.assertEquals(expectedSecond, actualSecond);
    
    Assert.assertNotSame(actualFirst, actualSecond);    
  }
  
}
