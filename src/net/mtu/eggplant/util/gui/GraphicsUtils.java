/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.gui;

import java.awt.Window;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Point;
import java.awt.FontMetrics;
import java.awt.BorderLayout;
import java.awt.Component;

import java.util.Vector;
import java.util.Enumeration;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JDialog;
  
import com.objectspace.jgl.Container;

/**
   graphics functions that should exist in java, but don't

   @author Jon Schewe
**/
public class GraphicsUtils {

  /**
     a basic main for testing a graphical class.  Takes the component and puts 
     * in in a JFrame or JDialog and shows the window.
     @param c the component to display
     @param dialog if <code>true</code> put in a JDialog, otherwise put in a
     * JFrame
     **/
  public static Window basicGUIMain(Component c, boolean dialog) {
    java.awt.Container container;
    Window window;
    
    if(dialog) {
      window = new JDialog();
      container = ((JDialog)window).getContentPane();
    }
    else {
      window = new JFrame();
      container = ((JFrame)window).getContentPane();      
    }

    window.setSize(c.getPreferredSize());
    window.addWindowListener(new BasicWindowMonitor());
    container.setLayout(new BorderLayout());
    container.add(c, BorderLayout.CENTER);
    window.pack();    
    window.show();

    return window;
  }
  
  /**
     draw the points on the Graphics Context.  Uses Graphics.drawPolyLine.
     @param g the graphics context
     @param points a vector of points, anything other than a point in this
     vector is simply skipped
  **/
  public static void drawPolyLine(Graphics g, Vector points) {
    int[] xpoints = new int[points.size()];
    int[] ypoints = new int[points.size()];
    int npoints = 0;
    Enumeration iter = points.elements();
    while(iter.hasMoreElements()) {
      Object obj = iter.nextElement();
      if(obj instanceof Point) {
        Point p = (Point)obj;
        xpoints[npoints] = p.x;
        ypoints[npoints] = p.y;
        npoints++;
      }
    }

    g.drawPolyline(xpoints, ypoints, npoints);
  }

  /**
     draw a bunch of polygons
     @param g the graphics context
     @param v a Container of polygons, other classes are ignored
  **/
  public static void drawPolygons(Graphics g, Container v) {
    drawPolygons(g, v.elements());
  }

  /**
     draw a bunch of polygons
     @param g the graphics context
     @param v a Vector of polygons, other classes are ignored
  **/
  public static void drawPolygons(Graphics g, Vector v) {
    drawPolygons(g, v.elements());
  }

  /**
     draw a bunch of polygons
     @param g the graphics context
     @param iter an Enumeration of polygons, other classes are ignored
  **/
  public static void drawPolygons(Graphics g, Enumeration iter) {
    while(iter.hasMoreElements()) {
      Object obj = iter.nextElement();
      if(obj instanceof Polygon) {
        g.drawPolygon((Polygon)obj);
      }
    }
  }

  /**
     fill a bunch of polygons
     @param g the graphics context
     @param v a Container of polygons, other classes are ignored
  **/
  public static void fillPolygons(Graphics g, Container v) {
    fillPolygons(g, v.elements());
  }

  /**
     fill a bunch of polygons
     @param g the graphics context
     @param v a Vector of polygons, other classes are ignored
  **/
  public static void fillPolygons(Graphics g, Vector v) {
    fillPolygons(g, v.elements());
  }

  /**
     fill a bunch of polygons
     @param g the graphics context
     @param iter an Enumeration of polygons, other classes are ignored
  **/
  public static void fillPolygons(Graphics g, Enumeration iter) {
    while(iter.hasMoreElements()) {
      Object obj = iter.nextElement();
      if(obj instanceof Polygon) {
        g.fillPolygon((Polygon)obj);
      }
    }
  }

  public static int getMaxWidth(JComboBox combo, FontMetrics fm) {
    int maxLen = 0;
    for(int i=0; i<combo.getItemCount(); i++) {
      String str = combo.getItemAt(i).toString();
      int wi = fm.stringWidth(str);
      if(wi > maxLen) {
        maxLen = wi;
      }
    }
    return maxLen + 20; // leave room for the scroll bar
  }

}
