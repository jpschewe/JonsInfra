/*
 * Copyright (c) 2000
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

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FilenameFilter;

/**
 * Rename all the packages we've changed since the last revision.
 * 
 * @version $Revision: 1.4 $
 */
final public class PackageRenamer {

  private static final int OLD = 0; // array index for "old" string
  private static final int NEW = 1; // array index for "new" string
  private static final int EXEMPT = 2; // starting array index for
  // exempt strings

  // Hardcode these for simplicity (no parsing data files)
  private static String[][] defaultReplaceKeys = {
    /* format is:
       {"old value", "new value", "exempt"}
       exempt is not required
    */
    //release 0.1 changes
    //{"org.tcfreenet.schewe.utils", "net.mtu.eggplant.util"},

    //changes for next release
  };

  private String _oldText;               // the original text
  private String getOldText() {
    return _oldText;
  }
  private void setOldText(final String t) {
    _oldText = t;
  }
  private StringBuffer _newText;         // the modified text
  final private StringBuffer getNewText() {
    return _newText;
  }
  final private void setNewText(final StringBuffer sb) {
    _newText = sb;
  }
  private int _changes;		    // the number of changes made
  final private int getChanges() {
    return _changes;
  }
  final private void setChanges(final int i) {
    _changes = i;
  }
  final private void incrementChanges() {
    _changes++;
  }
  final private static void usage() {
    System.err.println("java PackageRenamer " +
                       "<file1> [file2 ...]");
    System.exit(1);
  }

  final private static void error(final String msg) {
    System.err.println(msg);
    System.exit(1);
  }

  private int replace() {
    setChanges(0);
    if (getOldText() == null) {
      error("text input not initialized");
    }
    setNewText(replaceStrings(getOldText(), defaultReplaceKeys));
    return getChanges();
  }

  /** This method is designed to work for the specific
   * case where all the "old" strings being replaced
   * share a common search prefix, and each "old" string
   * may have a set of "exempt" cases which should NOT be 
   * replaced.
   *
   * @param replaceKeys null is not allowed in this array!
   *
   * @pre (source != null)
   * @pre (replaceKeys != null)
   */ 
  private StringBuffer replaceStrings(final String source, 
                                        final String replaceKeys[][]) {
    final StringBuffer result = new StringBuffer(source);

    for(int keynum = 0; keynum < replaceKeys.length; keynum++) {
      int from = 0;
      final String oldString = replaceKeys[keynum][OLD];
      final String newString = replaceKeys[keynum][NEW];
      /**
         @assert (oldString != null)
         @assert (newString != null)
      **/
      
      // Search for occurrence of search prefix
      boolean exemptFlag = true; // in case we don't find a match
      while(from < result.length()) {
        //System.out.println("top while");

        final int index = result.toString().indexOf(oldString, from);
        if(index != -1) { // found match
          exemptFlag = false;
          for (int k = EXEMPT; k < replaceKeys[keynum].length && !exemptFlag;
               k++) {
            //System.out.println("exempt loop");
            String exempt = replaceKeys[keynum][k];
            if (result.toString().regionMatches(index, exempt, 0,
                                                exempt.length())) {
                                // Found match on "exempt" string
              exemptFlag = true;
            }
          }
          if(!exemptFlag) {
            //System.out.println(index + " " + result.length());
            result.replace(index, index+oldString.length(), newString);
            from = index + newString.length();
            incrementChanges();
          } else {
            from = index + oldString.length();
          }
        } else {
          from = result.length()+1;
        }
      }
    }
    return result;
  }


  private void load(final File file) {
    try {
      char[] chars = new char[(int)file.length()];
      BufferedReader in = new BufferedReader(new FileReader(file));
      in.read(chars);
      in.close();
      setOldText(new String(chars));
    } catch (final Exception e) {
      error("failed reading " + file.getName() + ": " + e);
    }
  }

  private void save(final File file) {
    try {
      char[] chars = getNewText().toString().toCharArray();
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      out.write(chars);
      out.close();
    } catch (final IOException e) {
      error("failed writing " + file.getName() + ": " + e);
    }
  }

  /**
   * Entry point.
   *
   * @param file the File/directory to parse.  If this is a directory then
   * recursively look for files that end with ".java".
   *
   * @pre (file != null)
   **/
  public void recurse(final File file) {
    if(file.isFile() && file.getName().startsWith("PackageRenamer")) {
      //Don't change this file
      return;
    }
    
    if(file.isDirectory()) { // need to recurse
      System.out.println("Recursing down " + file.getName());
      // find all files and directories and check them
      final String[] files = file.list(getFilenameFilter());
      for(int i=0; i<files.length; i++) {
        final File f = new File(file, files[i]);
        recurse(f);
      }
      return;
    }

    load(file);

    final String filename = file.getName() + ": ";

    final int changes = replace();
    if (changes > 0) {
      // Save results to new file.
      final File newFile = new File(file.getAbsolutePath() + "-new");
      save(newFile);

      // Delete previous -old file, if it exists.
      final File oldFile = new File(file.getAbsolutePath() + "-old");
      if (oldFile.exists()) {
        oldFile.delete();
      }

      // Rename original file to filename-old.
      file.renameTo(oldFile);

      // Rename new file to filename.
      newFile.renameTo(new File(file.getAbsolutePath()));
      System.out.println(filename + Integer.toString(changes) +
                         ((changes > 1) ? " changes" : " change"));
    } 
  }
  
  public static void main(final String[] args) {
    int iArg = 0;
    final int nArgs = args.length;
    if (nArgs == 0) {
      usage();
    }

    // Load substitution file
    if (iArg == nArgs) {
      error("no files specified");
    }

    for(; iArg < nArgs; iArg++) {
      final String fileName = args[iArg];
      final File file = new File(fileName);
      if (!file.exists()) {
        System.err.println(fileName + " not found.");
        continue;
      }

      final PackageRenamer renamer = new PackageRenamer();
      // do everything
      renamer.recurse(file);
    }
  }

  private FilenameFilter getFilenameFilter() {
    return _javaFilenameFilter;
  }
  
  private FilenameFilter _javaFilenameFilter = new FilenameFilter() {
    /**
     * @pre (dir != null)
     * @pre (filename != null)
     */
    public boolean accept(final File dir, final String filename) {
      final File testing = new File(dir, filename);
      if(testing.isDirectory()) {
        return true;
      }
      if(filename.endsWith(".java")) {
        return true;
      }
      return false;
    }
  };
  
}
