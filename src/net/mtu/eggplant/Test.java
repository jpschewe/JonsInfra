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
package net.mtu.eggplant;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

/**
 * @version $Revision: 1.4 $
 */
public class Test {
  public static void main(String[] args) {
    try {

      Class classObj = Convertsql.class;
      URL url = classObj.getResource("Convertsql.javax");
      if(url != null) {
        System.out.println("Found file: " + url);
      }
      else {
        System.out.println("File does not exist");
      }
      
      System.exit(0);
      
      /*
      Thread t = new Thread(new Runnable() {
        public void run() {
          for(;;) {
            try {
              Thread.sleep(500);
            }
            catch(Exception e) {}
            System.out.println("awake");
          }
        }
      }, "testing");
      t.start();
      */
      //quit(0);

      JFrame frame = new JFrame("file lister");
      frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent we) {
          quit(0);
        }
      });
      frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),
                                                     BoxLayout.X_AXIS));

      /*
      JFileDialog fd = new JFileDialog(frame, true, ".");
      fd.show();
      */

      frame.pack();
      frame.show();

    }
    catch(java.lang.Exception e) {
      System.err.println("An error occurred " + e);
      e.printStackTrace();
      quit(1);
    }
  }
  
  public static void quit(int signal) {
    System.exit(signal);
  }

  public static void getListing(File dir, Vector fileList, Vector dirList) {
    String[] listing = dir.list();
    File f;
    dirList.addElement("..");
    for(int i=0; i<listing.length; i++) {
      f = new File(listing[i]);
      if(f.isDirectory()) {
        dirList.addElement(listing[i]);
      }
      else {
        fileList.addElement(listing[i]);
      }
    }
  }
}
  
