/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
   This class makes it easy to accept files by extension.
**/
public class BasicFileFilter extends FileFilter {

  /**
     Create a Filter filter with description and allowing files with
     extensions in the list extensions.

     @param description the description of this filter
     @param extensions the extensions to allow

     @pre (description != null)
     @pre (extensions != null)
  **/
  public BasicFileFilter(final String description, final String[] extensions) {
    _description = description;
    _extensions = extensions;
  }

  /**
     Create a Filter filter with description and allowing files with
     extensions equal to extension

     @param description the description of this filter
     @param extension the extension to allow

     @pre (description != null)
     @pre (extension != null)
  **/
  public BasicFileFilter(final String description, final String extension) {
    _description = description;
    _extensions = new String[1];
    _extensions[0] = extension;
  }
  
  private String _description;

  private String[] _extensions;

  public boolean accept(final File f) {
    //Only accept files
    if(!f.isFile()) {
      return false;
    }

    final String filename = f.getName();
    for(int i=0; i<_extensions.length; i++) {
      if(filename.endsWith('.' + _extensions[i])) {
        return true;
      }
    }
    return false;
  }

  public String getDescription() {
    return _description;
  }

}
