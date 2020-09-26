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
package net.mtu.eggplant.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.mtu.eggplant.util.BasicFileFilter;
import net.mtu.eggplant.util.gui.GraphicsUtils;

/**
 * Validate a schema.
 */
public class SchemaValidator {

  /**
   * @param args ignored
   */
  public static void main(final String[] args) {
    final File initialDirectory = getInitialDirectory();
    final JFileChooser chooser = new JFileChooser(initialDirectory);
    chooser.setDialogTitle("Choose an XML schema to parse");
    chooser.setFileFilter(new BasicFileFilter("Schema Files", "xsd"));
    while (true) {
      final int state = chooser.showOpenDialog(null);
      if (JFileChooser.APPROVE_OPTION == state) {
        final File file = chooser.getSelectedFile();
        setInitialDirectory(file);
        try {
          parseSchema(file);
          JOptionPane.showMessageDialog(null, "Parse Successful", "Error", JOptionPane.INFORMATION_MESSAGE);
        } catch (final IOException e) {
          GraphicsUtils.error(e.getMessage());
        } catch (final SAXParseException spe) {
          GraphicsUtils.error("Error parsing schema "
              + " line: "
              + spe.getLineNumber()
              + " column: "
              + spe.getColumnNumber()
              + " "
              + spe.getMessage());
        } catch (final SAXException e) {
          GraphicsUtils.error(e.getMessage());
        } catch (final ClassCastException e) {
          GraphicsUtils.error(e.getMessage());
        }
      } else {
        return;
      }
    }
  }

  /**
   * @param xsdFile file to parse
   * @return the parsed schema
   * @throws IOException on error reading the file
   * @throws FileNotFoundException if the file does not exist
   * @throws SAXException on an error parsing the schema
   */
  public static Schema parseSchema(final File xsdFile) throws FileNotFoundException, IOException, SAXException {
    try (FileInputStream stream = new FileInputStream(xsdFile)) {

      final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

      factory.setResourceResolver(new Resolver(xsdFile.getParent()));

      final Source schemaFile = new StreamSource(xsdFile);
      final Schema schema = factory.newSchema(schemaFile);
      return schema;
    }
  }

  /**
   * Set the initial directory preference. This supports opening new file
   * dialogs to a (hopefully) better default in the user's next session.
   *
   * @param dir the File for the directory in which file dialogs should open
   */
  private static void setInitialDirectory(final File dir) {
    // Store only directories
    final String directory;
    if (dir.isDirectory()) {
      directory = dir.toString();
    } else {
      directory = dir.getParent();
    }

    final Preferences preferences = Preferences.userNodeForPackage(SchemaValidator.class);
    final String previousPath = preferences.get(INITIAL_DIRECTORY_PREFERENCE_KEY, null);

    if (!Objects.equals(directory, previousPath)) {
      preferences.put(INITIAL_DIRECTORY_PREFERENCE_KEY, directory);
    }
  }

  private static final class Resolver implements LSResourceResolver {
    private final DOMImplementationRegistry registry;

    private final DOMImplementationLS domImplementationLS;

    private final @Nullable String searchPath;

    public Resolver(final @Nullable String searchPath) {
      this.searchPath = searchPath;
      // get an instance of the DOMImplementation registry
      try {
        this.registry = DOMImplementationRegistry.newInstance();
        this.domImplementationLS = (DOMImplementationLS) registry.getDOMImplementation("LS");
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
        throw new RuntimeException("Unable to get DOM registry", e);
      }
    }

    @Override
    @SuppressFBWarnings(value = { "OBL_UNSATISFIED_OBLIGATION" }, justification = "Input must be cleaned up by caller")
    public @Nullable LSInput resolveResource(final String type,
                                             final @Nullable String namespaceURI,
                                             final @Nullable String publicId,
                                             final @Nullable String systemId,
                                             final @Nullable String baseURI) {

      if (null == systemId) {
        return null;
      }
      final LSInput input = domImplementationLS.createLSInput();
      input.setBaseURI(baseURI);
      input.setPublicId(publicId);
      input.setSystemId(systemId);

      try {
        final Reader inputStream;
        if (systemId.startsWith("/")) {
          inputStream = new InputStreamReader(new FileInputStream(systemId), Charset.defaultCharset());
        } else {
          final File resource = new File(searchPath, systemId);
          inputStream = new InputStreamReader(new FileInputStream(resource), Charset.defaultCharset());
        }
        input.setCharacterStream(inputStream);

        return input;
      } catch (final FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Get the initial directory to which file dialogs should open. This supports
   * opening to a better directory across sessions.
   *
   * @return the File for the initial directory
   */
  private static File getInitialDirectory() {
    final Preferences preferences = Preferences.userNodeForPackage(SchemaValidator.class);
    final String path = preferences.get(INITIAL_DIRECTORY_PREFERENCE_KEY, null);

    if (null != path) {
      return new File(path);
    } else {
      return new File(".");
    }
  }

  /**
   * Preferences key for file dialog initial directory
   */
  private static final String INITIAL_DIRECTORY_PREFERENCE_KEY = "InitialDirectory";

}
