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

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import net.mtu.eggplant.util.Named;

/**
 * <p>Class that knows how to render lots of things.  If you want to create a
 * renderer, just subclass this SmartRenderer and override the
 * getStringValue() method.  For any object that is not a known type toString
 * is used.  Null values are always displayed as "NULL".  This class counts on
 * the fact that the default renderers for list, trable and tree all return a
 * subclass of JLabel.  If that ever changes, this class breaks.<p>
 *
 * <p>Known types</p>
 * <ul>
 *   <li>Named</li>
 *   <li>Icon</li>
 * </ul>
 * 
 * @version $Revision: 1.7 $
 */
public class SmartRenderer implements ListCellRenderer, TableCellRenderer, TreeCellRenderer {

  public SmartRenderer() {
    _treeRenderer = new DefaultTreeCellRenderer();
    _listRenderer = new DefaultListCellRenderer();
    _tableRenderer = new DefaultTableCellRenderer();
  }

  private DefaultTreeCellRenderer _treeRenderer;
  private DefaultListCellRenderer _listRenderer;
  private DefaultTableCellRenderer _tableRenderer;
  
  public final Component getListCellRendererComponent(final JList list,
                                                      final Object value,
                                                      final int index,
                                                      final boolean isSelected,
                                                      final boolean cellHasFocus) {

    final JLabel renderer = (JLabel)_listRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    final String text;
    if(value == null) {
      text = "NULL";
    } else if (value instanceof Icon) {
      text = "";
    } else {
      text = getStringValue(value);
    }
    renderer.setText(text);
    
    return renderer;
  }

  public final Component getTableCellRendererComponent(final JTable table,
                                                       final Object value,
                                                       final boolean isSelected,
                                                       final boolean hasFocus,
                                                       final int row,
                                                       final int column) {


    final JLabel renderer = (JLabel)_tableRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    final String text;
    if(value == null) {
      text = "NULL";
    } else if (value instanceof Icon) {
      text = "";
    } else {
      text = getStringValue(value);
    }
    renderer.setText(text);
    
    return renderer;
  }

  public Component getTreeCellRendererComponent(final JTree tree,
                                                final Object value,
                                                final boolean selected,
                                                final boolean expanded,
                                                final boolean leaf,
                                                final int row,
                                                final boolean hasFocus) {

    final JLabel renderer = (JLabel)_treeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    final String text;
    if(value == null) {
      text = "NULL";
    } else if (value instanceof Icon) {
      text = "";
    } else {
      text = getStringValue(value);
    }
    renderer.setText(text);
    
    return renderer;
  }

  /**
     Get the string representation of this object.
  **/
  public String getStringValue(final Object value) {
    if(value instanceof Named) {
      return ((Named)value).getName();
    } else {
      return value.toString();
    }

  }

  //final private static Border _emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
  
}
