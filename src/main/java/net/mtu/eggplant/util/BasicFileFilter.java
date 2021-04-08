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

import static org.checkerframework.checker.nullness.util.NullnessUtil.castNonNullDeep;

import java.io.File;
import java.util.Arrays;

import javax.swing.filechooser.FileFilter;

/**
 * This class makes it easy to accept files by extension.
 */
public class BasicFileFilter extends FileFilter {

  /**
   * Create a Filter filter with description and allowing files with extensions
   * in the list extensions.
   *
   * @param description the description of this filter
   * @param extensions the extensions to allow
   */
  public BasicFileFilter(final String description,
                         final String[] extensions) {
    this.description = description;
    this.extensions = castNonNullDeep(Arrays.copyOf(extensions, extensions.length));
  }

  /**
   * Create a Filter filter with description and allowing files with extensions
   * equal to extension as well as directories (for traversal).
   *
   * @param description the description of this filter
   * @param extension the extension to allow
   */
  public BasicFileFilter(final String description,
                         final String extension) {
    this.description = description;
    this.extensions = new String[1];
    extensions[0] = extension;
  }

  private final String description;

  private final String[] extensions;

  @Override
  public boolean accept(final File f) {
    // accept directories, otherwise can't browse into directories
    if (f.isDirectory()) {
      return true;
    }

    final String filename = f.getName();
    for (final String extension : extensions) {
      if (filename.endsWith('.'
          + extension)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getDescription() {
    return description;
  }

}
