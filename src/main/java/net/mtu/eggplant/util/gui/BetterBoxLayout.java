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
package net.mtu.eggplant.util.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * Lets box be it's preferred size and lays out components inside accordingly.
 */
public class BetterBoxLayout implements LayoutManager2 {

  /**
   * Possible orientation options.
   */
  public enum Orientation {
    /**
     * Layout components along the Y axis.
     */
    VERTICAL,
    /**
     * Layout components along the X axis.
     */
    HORIZONTAL;
  }

  /**
   * Create a BetterBoxLayout with a HORIZONTAL orientation, ie. components laid
   * out along the X axis.
   **/
  public BetterBoxLayout() {
    this(Orientation.HORIZONTAL);
  }

  /**
   * Create a BetterBoxLayout with given orientation.
   *
   * @param orientation the orientation of the layout
   **/
  public BetterBoxLayout(final Orientation orientation) {
    this.orientation = orientation;
  }

  private final Orientation orientation;

  @Override
  public void addLayoutComponent(final String name,
                                 final Component comp) {

  }

  @Override
  public void addLayoutComponent(final Component comp,
                                 final Object constraints) {

  }

  @Override
  public void layoutContainer(final Container target) {
    final Insets insets = target.getInsets();
    final Dimension targetSize = target.getSize();
    final Component[] components = target.getComponents();

    if (orientation == Orientation.VERTICAL) {
      final int componentWidth = targetSize.width
          - insets.left
          - insets.right;
      final int x = insets.left;
      int y = insets.top;
      for (final Component component : components) {
        final int height = component.getPreferredSize().height;
        component.setBounds(x, y, componentWidth, height);
        y += height;
      }
    } else {
      final int componentHeight = targetSize.height
          - insets.top
          - insets.bottom;
      int x = insets.left;
      final int y = insets.top;
      for (final Component component : components) {
        final int width = component.getPreferredSize().width;
        component.setBounds(x, y, width, componentHeight);
        x += width;
      }
    }
  }

  @Override
  public Dimension minimumLayoutSize(final Container target) {
    return preferredLayoutSize(target);
  }

  @Override
  public Dimension preferredLayoutSize(final Container target) {
    final Insets insets = target.getInsets();
    final Dimension dim = new Dimension(0, 0);
    final Component[] components = target.getComponents();
    for (final Component component : components) {
      final Dimension preferredSize = component.getPreferredSize();
      if (orientation == Orientation.VERTICAL) {
        dim.height += preferredSize.height;
        dim.width = Math.max(preferredSize.width, dim.width);
      } else {
        dim.height = Math.max(preferredSize.height, dim.height);
        dim.width += preferredSize.width;
      }
    }

    dim.height += insets.top
        + insets.bottom;
    dim.width += insets.left
        + insets.right;
    return dim;
  }

  @Override
  public Dimension maximumLayoutSize(final Container target) {
    return preferredLayoutSize(target);
  }

  @Override
  public void removeLayoutComponent(final Component comp) {

  }

  @Override
  public float getLayoutAlignmentX(final Container target) {
    return 0F;
  }

  @Override
  public float getLayoutAlignmentY(final Container target) {
    return 0F;
  }

  @Override
  public void invalidateLayout(final Container target) {

  }

}
