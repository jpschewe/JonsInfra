/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.gui;

import java.awt.LayoutManager2;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Insets;

/**
   Lets box be it's preferred size and lays out components inside accordingly.
   Currently only veritcal layout is supported.
**/
public class BetterBoxLayout implements LayoutManager2 {

  /**
     Layout components along the Y axis.
  **/
  final static public BetterBoxLayout_Orientation VERTICAL = new BetterBoxLayout_Orientation();
  /**
     Layout components along the X axis.
  **/
  final static public BetterBoxLayout_Orientation HORIZONTAL = new BetterBoxLayout_Orientation();

  /**
     Create a BetterBoxLayout with a HORIZONTAL orientation, ie. components
     laid out along the X axis.
  **/
  public BetterBoxLayout() {
    this(HORIZONTAL);
  }

  /**
     Create a BetterBoxLayout with given orientation.
  **/
  public BetterBoxLayout(final BetterBoxLayout_Orientation orientation ) {
    _orientation = orientation;
  }
  
  private BetterBoxLayout_Orientation _orientation;
  
  public void addLayoutComponent(final String name, final Component comp) {

  }

  public void addLayoutComponent(final Component comp, final Object constraints) {

  }
  
  public void layoutContainer(final Container target) {
    final Insets insets = target.getInsets();
    final Dimension targetSize = target.getSize();
    final Component[] components = target.getComponents();

    if(_orientation == VERTICAL) {
      final int componentWidth = targetSize.width - insets.left - insets.right;
      final int x = insets.left;
      int y = insets.top;
      for(int i=0; i<components.length; i++) {
        final int height = components[i].getPreferredSize().height;
        components[i].setBounds(x, y, componentWidth, height);
        y += height;
      }
    } else {
      final int componentHeight = targetSize.height - insets.top - insets.bottom;
      int x = insets.left;
      final int y = insets.top;
      for(int i=0; i<components.length; i++) {
        final int width = components[i].getPreferredSize().width;
        components[i].setBounds(x, y, width, componentHeight);
        x += width;
      }
    }
  }

  public Dimension minimumLayoutSize(final Container target) {
    return preferredLayoutSize(target);
  }

  public Dimension preferredLayoutSize(final Container target) {
    final Insets insets = target.getInsets();
    final Dimension dim = new Dimension(0, 0);
    final Component[] components = target.getComponents();
    for(int i=0; i<components.length; i++) {
      final Dimension preferredSize = components[i].getPreferredSize();
      if(_orientation == VERTICAL) {
        dim.height += preferredSize.height;
        dim.width = Math.max(preferredSize.width, dim.width);
      } else {
        dim.height = Math.max(preferredSize.height, dim.height);
        dim.width += preferredSize.width;
      }
    }
    
    dim.height += insets.top + insets.bottom;
    dim.width += insets.left + insets.right;
    return dim;
  }

  public Dimension maximumLayoutSize(final Container target) {
    return preferredLayoutSize(target);
  }
  
  public void removeLayoutComponent(final Component comp) {

  }

  public float getLayoutAlignmentX(final Container target) {
    return 0F;
  }

  public float getLayoutAlignmentY(final Container target) {
    return 0F;
  }

  public void invalidateLayout(final Container target) {

  }

  /**
     Private class to simulate enum types.
  **/
  static private class BetterBoxLayout_Orientation {}
  
}
