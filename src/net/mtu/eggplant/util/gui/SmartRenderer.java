/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.gui;

import org.tcfreenet.schewe.utils.Named;
import org.tcfreenet.schewe.utils.Unique;

import java.awt.Component;

import javax.swing.ListCellRenderer;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
   <p>Class that knows how to render lots of things.  If you want to create a
   renderer, just subclass this SmartRenderer and override the
   getStringValue() method.  For any object that is not a known type toString
   is used.  Null values are always displayed as "NULL"<p>

   <p>Known types</p>
   <ul>
     <li>Unique</li>
     <li>Named</li>
     <li>Icon</li>
   </ul>
   
**/
public class SmartRenderer extends JLabel implements ListCellRenderer, TableCellRenderer, TreeCellRenderer {

  public SmartRenderer() {
    setOpaque(true);
  }
  
  public Component getListCellRendererComponent(final JList list,
                                                final Object value,
                                                final int index,
                                                final boolean isSelected,
                                                final boolean cellHasFocus) {
    final String text;
    if(value == null) {
      text = "NULL";
    } else if (value instanceof Icon) {
      setIcon((Icon)value);
      text = "";
    } else {
      text = getStringValue(value);
    }
    setText(text);

    //For accessibility
    setComponentOrientation(list.getComponentOrientation());
        
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setBorder((cellHasFocus) ? UIManager.getBorder("List.focusCellHighlightBorder") : _emptyBorder);
    
    return this;
  }

  public Component getTableCellRendererComponent(final JTable table,
                                                 final Object value,
                                                 final boolean isSelected,
                                                 final boolean hasFocus,
                                                 final int row,
                                                 final int column) {


    return null;
  }

  public Component getTreeCellRendererComponent(final JTree tree,
                                                final Object value,
                                                final boolean selected,
                                                final boolean expanded,
                                                final boolean leaf,
                                                final int row,
                                                final boolean hasFocus) {

    return null;
  }

  /**
     Get the string representation of this object.
  **/
  public String getStringValue(final Object value) {
    if(value instanceof Named) {
      return ((Named)value).getName();
    } else if(value instanceof Unique) {
      return Long.toString(((Unique)value).getUID());
    } else {
    return value.toString();
    }

  }

  final private static Border _emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
  
}
