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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.mtu.eggplant.util.BasicFileFilter;
import net.mtu.eggplant.util.gui.GraphicsUtils;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Validate a schema.
 * 
 */
public class SchemaValidator {

	/**
	 * @param args
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
					JOptionPane.showMessageDialog(null, "Parse Successful",
							"Error", JOptionPane.INFORMATION_MESSAGE);
				} catch (final IOException e) {
					GraphicsUtils.error(e.getMessage());
				} catch (final SAXParseException spe) {
					GraphicsUtils.error("Error parsing schema " + " line: "
							+ spe.getLineNumber() + " column: "
							+ spe.getColumnNumber() + " " + spe.getMessage());
				} catch (final SAXException e) {
					GraphicsUtils.error(e.getMessage());
				} catch (final ClassCastException e) {
					GraphicsUtils.error(e.getMessage());
				} catch (final ClassNotFoundException e) {
					GraphicsUtils.error(e.getMessage());
				} catch (final InstantiationException e) {
					GraphicsUtils.error(e.getMessage());
				} catch (final IllegalAccessException e) {
					GraphicsUtils.error(e.getMessage());
				}
			} else {
				return;
			}
		}
	}

	public static Schema parseSchema(final File xsdFile) throws SAXException,
			ClassCastException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, IOException {
		final FileInputStream stream = new FileInputStream(xsdFile);

		// get an instance of the DOMImplementation registry
		final DOMImplementationRegistry registry = DOMImplementationRegistry
				.newInstance();
		final DOMImplementationLS domImplementationLS = (DOMImplementationLS) registry
				.getDOMImplementation("LS");

		final SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		factory.setResourceResolver(new LSResourceResolver() {
			public LSInput resolveResource(final String type,
					final String namespaceURI, final String publicId,
					final String systemId, final String baseURI) {

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
						inputStream = new FileReader(systemId);
					} else {
						final File resource = new File(xsdFile.getParent(), systemId);
						inputStream = new FileReader(resource);
					}
					input.setCharacterStream(inputStream);

					return input;
				} catch (final FileNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		});

		final Source schemaFile = new StreamSource(xsdFile);
		final Schema schema = factory.newSchema(schemaFile);
		stream.close();
		return schema;
	}

	/**
	 * Set the initial directory preference. This supports opening new file
	 * dialogs to a (hopefully) better default in the user's next session.
	 * 
	 * @param dir
	 *            the File for the directory in which file dialogs should open
	 */
	private static void setInitialDirectory(final File dir) {
		// Store only directories
		final File directory;
		if (dir.isDirectory()) {
			directory = dir;
		} else {
			directory = dir.getParentFile();
		}

		final Preferences preferences = Preferences
				.userNodeForPackage(SchemaValidator.class);
		final String previousPath = preferences.get(
				INITIAL_DIRECTORY_PREFERENCE_KEY, null);

		if (!directory.toString().equals(previousPath)) {
			preferences.put(INITIAL_DIRECTORY_PREFERENCE_KEY, directory
					.toString());
		}
	}

	/**
	 * Get the initial directory to which file dialogs should open. This
	 * supports opening to a better directory across sessions.
	 * 
	 * @return the File for the initial directory
	 */
	private static File getInitialDirectory() {
		final Preferences preferences = Preferences
				.userNodeForPackage(SchemaValidator.class);
		final String path = preferences.get(INITIAL_DIRECTORY_PREFERENCE_KEY,
				null);

		File dir = null;
		if (null != path) {
			dir = new File(path);
		}
		return dir;
	}

	/**
	 * Preferences key for file dialog initial directory
	 */
	private static final String INITIAL_DIRECTORY_PREFERENCE_KEY = "InitialDirectory";

}
