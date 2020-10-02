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
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.custommonkey.xmlunit.Diff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Some utilities for working with XML.
 */
public final class XMLUtils {

  private XMLUtils() {
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtils.class);

  /**
   * Standard document builder instance. Uses {@link #STANDARD_ERROR_HANDLER}.
   */
  public static final DocumentBuilder DOCUMENT_BUILDER;

  /**
   * Standard error handler that throws exceptions on error and fatalError and
   * logs a warning on warning.
   */
  public static final ErrorHandler STANDARD_ERROR_HANDLER = new ErrorHandler() {
    @Override
    public void error(final SAXParseException spe) throws SAXParseException {
      throw spe;
    }

    @Override
    public void fatalError(final SAXParseException spe) throws SAXParseException {
      throw spe;
    }

    @Override
    public void warning(final SAXParseException spe) throws SAXParseException {
      LOGGER.warn(spe.getMessage(), spe);
    }
  };

  // create basic document builder
  static {
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      DOCUMENT_BUILDER = factory.newDocumentBuilder();
      DOCUMENT_BUILDER.setErrorHandler(STANDARD_ERROR_HANDLER);
    } catch (final ParserConfigurationException pce) {
      throw new RuntimeException(pce.getMessage(), pce);
    }
  }

  /**
   * Parse the document from the given stream. The document is validated with
   * the specified schema. Does not close the stream after reading. Warnings are
   * output to the logger for this class.
   *
   * @param stream a stream containing document
   * @param schema used to validate the document
   * @return the document
   * @throws IOException if there is an error reading the stream
   * @throws SAXException if there is an error parsing the document or it
   *           doesn't match the schema
   * @throws RuntimeException if there is an error configuring the XML parser,
   *           this shouldn't happen
   */
  public static Document parse(final Reader stream,
                               final Schema schema)
      throws IOException, SAXException {
    try {
      final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builderFactory.setNamespaceAware(true);
      builderFactory.setSchema(schema);
      builderFactory.setIgnoringComments(true);
      builderFactory.setIgnoringElementContentWhitespace(true);
      final DocumentBuilder parser = builderFactory.newDocumentBuilder();

      parser.setErrorHandler(STANDARD_ERROR_HANDLER);

      // parse
      final StringWriter writer = new StringWriter();
      stream.transferTo(writer);
      final String content = writer.toString();
      final Document document = parser.parse(new InputSource(new StringReader(content)));

      return document;
    } catch (final ParserConfigurationException e) {
      throw new RuntimeException("Error configuring the XML parser", e);
    }
  }

  /**
   * Parse xmlDoc an XML document. Just does basic parsing, no validity checks.
   * Does not close the stream after parsing. Warnings are output to the logger
   * for this class.
   *
   * @param xmlDocStream where to read the document from
   * @throws IOException if there is an error reading the stream
   * @throws SAXException if the document is found to be invalid
   * @throws RuntimeException if there is an error configuring the XML parser,
   *           this shouldn't happen
   * @return {@link #parseXMLDocument(InputSource)
   */
  public static Document parseXMLDocument(final InputStream xmlDocStream) throws SAXException, IOException {
    return parseXMLDocument(new InputSource(xmlDocStream));
  }

  /**
   * Parse xmlDoc an XML document. Just does basic parsing, no validity checks.
   * Does not close the stream after parsing. Warnings are output to the logger
   * for this class.
   *
   * @param xmlDocStream where to read the document from
   * @throws IOException if there is an error reading the stream
   * @throws SAXException if the document is found to be invalid
   * @throws RuntimeException if there is an error configuring the XML parser,
   *           this shouldn't happen
   * @return {@link #parseXMLDocument(InputSource)}
   */
  public static Document parseXMLDocument(final Reader xmlDocStream) throws SAXException, IOException {
    return parseXMLDocument(new InputSource(xmlDocStream));
  }

  /**
   * Parse xmlDoc an XML document. Just does basic parsing, no validity checks.
   * Does not close the stream after parsing. Warnings are output to the logger
   * for this class.
   *
   * @param xmlDocStream where to read the document from
   * @return the parsed document
   * @throws IOException if there is an error reading the stream
   * @throws SAXException if the document is found to be invalid
   * @throws RuntimeException if there is an error configuring the XML parser,
   *           this shouldn't happen
   */
  public static Document parseXMLDocument(final InputSource xmlDocStream) throws SAXException, IOException {
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setIgnoringComments(true);
      factory.setIgnoringElementContentWhitespace(true);

      final DocumentBuilder parser = factory.newDocumentBuilder();
      parser.setErrorHandler(STANDARD_ERROR_HANDLER);

      final Document document = parser.parse(xmlDocStream);
      return document;
    } catch (final ParserConfigurationException pce) {
      throw new RuntimeException("Error configuring the XML parser", pce);
    }
  }

  /**
   * Get a string value from an attribute.
   *
   * @param element the element to get the attribute from, may be null
   * @param attributeName the attribute name to get
   * @return the value, null if the attribute value is empty
   */
  public static @Nullable String getStringAttributeValue(final Element element,
                                                         final String attributeName) {
    final String str = element.getAttribute(attributeName);
    return str;
  }

  /**
   * Get a boolean value from an attribute.
   *
   * @param element the element to get the attribute from
   * @param attributeName the attribute name to get
   * @return the value, if the attribute value is empty
   */
  @SuppressFBWarnings(value = { "NP_BOOLEAN_RETURN_NULL" }, justification = "Need to return Null so that we can determine when there is no value")
  public static @Nullable Boolean getBooleanAttributeValue(final Element element,
                                                           final String attributeName) {
    final String str = element.getAttribute(attributeName);
    if (str.isEmpty()) {
      return null;
    } else {
      return Boolean.valueOf(str);
    }
  }

  /**
   * Get a double value from an attribute.
   *
   * @param element the element to get the attribute from
   * @param attributeName the attribute name to get
   * @return the value, null if attribute value is empty
   */
  public static @Nullable Double getDoubleAttributeValue(final Element element,
                                                         final String attributeName) {
    final String str = element.getAttribute(attributeName);
    if (str.isEmpty()) {
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
    writeXML(doc, writer, null);
  }

  /**
   * Write the document to a writer.
   *
   * @param doc the document to write
   * @param writer where to write the document
   * @param encoding if non-null use this as the encoding for the text
   * @throws RuntimeException if a {@link TransformerException} occurs.
   */
  public static void writeXML(final Document doc,
                              final Writer writer,
                              final @Nullable String encoding) {
    try {
      final TransformerFactory transformerFactory = TransformerFactory.newInstance();
      final Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      if (null != encoding) {
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
      }
      final DOMSource source = new DOMSource(doc);
      final StreamResult result = new StreamResult(writer);
      transformer.transform(source, result);
    } catch (final TransformerException e) {
      throw new RuntimeException("Internal error writing xml", e);
    }
  }

  /**
   * Compare two documents and check if they are the same or not.
   *
   * @param controlDoc first document to compare
   * @param testDoc the second document to compare
   * @return true if the documents have the same elements and attributes,
   *         regardless of order
   */
  public static boolean compareDocuments(final Document controlDoc,
                                         final Document testDoc) {
    final Diff xmldiff = new Diff(controlDoc, testDoc);
    return xmldiff.similar();
  }

}
