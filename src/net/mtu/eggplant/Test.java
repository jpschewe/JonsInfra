package net.mtu.eggplant;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

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
  
