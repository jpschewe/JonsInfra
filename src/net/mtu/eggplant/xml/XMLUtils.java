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

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Some utilities for working with XML.
 */
public class XMLUtils {

  public static final DocumentBuilder DOCUMENT_BUILDER;

  // create basic document builder
  static {
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DOCUMENT_BUILDER = factory.newDocumentBuilder();
      DOCUMENT_BUILDER.setErrorHandler(new ErrorHandler() {
        public void error(final SAXParseException spe) throws SAXParseException {
          throw spe;
        }

        public void fatalError(final SAXParseException spe) throws SAXParseException {
          throw spe;
        }

        public void warning(final SAXParseException spe) throws SAXParseException {
          System.err.println(spe.getMessage());
        }
      });
    } catch (final ParserConfigurationException pce) {
      throw new RuntimeException(pce.getMessage());
    }
  }

  /**
   * Parse xmlDoc an XML document. Just does basic parsing, no validity checks.
   */
  public static Document parseXMLDocument(final InputStream xmlDocStream) {
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setIgnoringComments(true);
      factory.setIgnoringElementContentWhitespace(true);

      final DocumentBuilder parser = factory.newDocumentBuilder();
      parser.setErrorHandler(new ErrorHandler() {
        public void error(final SAXParseException spe) throws SAXParseException {
          throw spe;
        }

        public void fatalError(final SAXParseException spe) throws SAXParseException {
          throw spe;
        }

        public void warning(final SAXParseException spe) throws SAXParseException {
          System.err.println(spe.getMessage());
        }
      });

      final Document document = parser.parse(xmlDocStream);
      return document;
    } catch (final ParserConfigurationException pce) {
      throw new RuntimeException(pce.getMessage());
    } catch (final SAXException se) {
      throw new RuntimeException(se.getMessage());
    } catch (final IOException ioe) {
      throw new RuntimeException(ioe.getMessage());
    }
  }

  /**
   * Filter the {@link NodeList} to only Elements.
   * 
   * @param nodelist cannot be null
   * @return the list of {@link Element}s
   */
  public static List<Element> filterToElements(final NodeList nodelist) {
    final List<Element> retval = new ArrayList<Element>(nodelist.getLength());
    for (int i = 0; i < nodelist.getLength(); ++i) {
      final Node node = nodelist.item(i);
      if (node instanceof Element) {
        retval.add((Element) node);
      }
    }
    return retval;
  }

  /**
   * Iterate over Elements only. This is like
   * {@link #filterToElements(NodeList)}, except that it doesn't incur hte
   * overhead of creating the list.
   */
  public static Iterator<Element> getElementIterator(final NodeList nodelist) {
    return new ElementIterator(nodelist);
  }

  /**
   * @see #getDoubleAttributeValue(Element, String)
   */
  public static String getStringAttributeValue(final Element element,
                                               final String attributeName) {
    if (null == element) {
      return null;
    }
    final String str = element.getAttribute(attributeName);
    return str;
  }

  /**
   * @see #getDoubleAttributeValue(Element, String)
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = { "NP_BOOLEAN_RETURN_NULL" }, justification = "Need to return Null so that we can determine when there is no score")
  public static Boolean getBooleanAttributeValue(final Element element,
                                                 final String attributeName) {
    if (null == element) {
      return null;
    }
    final String str = element.getAttribute(attributeName);
    if (null == str
        || "".equals(str)) {
      return null;
    } else {
      return Boolean.valueOf(str);
    }
  }

  /**
   * Get a double value from an attribute.
   * 
   * @param element the element to get the attribute from, may be null
   * @param attributeName the attribute name to get
   * @return the value, null if element is null or the attribute value is null
   *         or empty
   */
  public static Double getDoubleAttributeValue(final Element element,
                                               final String attributeName) {
    if (null == element) {
      return null;
    }
    final String str = element.getAttribute(attributeName);
    if (null == str
        || "".equals(str)) {
      return null;
    } else {
      return Double.valueOf(str);
    }
  }

  /**
   * Write the document to a writer.
   * 
   * @param doc the document to write
   * @param writer where to write the document
   */
  public static void writeXML(final Document doc,
                              final Writer writer) {
    try {
      final TransformerFactory transformerFactory = TransformerFactory.newInstance();
      final Transformer transformer = transformerFactory.newTransformer();
      final DOMSource source = new DOMSource(doc);
      final StreamResult result = new StreamResult(writer);
      transformer.transform(source, result);
    } catch (final TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Iterate over the Elements in the node list.
   */
  private static final class ElementIterator implements Iterator<Element> {
    private final NodeList nodelist;

    private int currentIndex;

    private int nextIndex;

    public ElementIterator(final NodeList nodelist) {
      this.nodelist = nodelist;
      this.currentIndex = 0;
      this.nextIndex = 0;
    }

    public boolean hasNext() {
      advanceToNextElement();
      return nextIndex < nodelist.getLength();
    }

    public Element next() {
      if (hasNext()) {
        return (Element) nodelist.item(nextIndex);
      } else {
        throw new NoSuchElementException();
      }
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    private void advanceToNextElement() {
      if (currentIndex >= nextIndex) {
        do {
          ++nextIndex;
        } while (nextIndex < nodelist.getLength()
            && !(nodelist.item(nextIndex) instanceof Element));

        // nothing to do if next is already past current
      }
    }
  }
}
