/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.gui;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
   utilities for working with tables
**/
final public class TableUtils {

  private TableUtils() {
    // don't create instances
  }
  
  /**
     set the widths of all of the columns to the max width of the object in
     the column.

     @pre (table != null)
  **/
  public static void setColumnMinWidths(final JTable table) {
    final TableColumnModel colModel = table.getColumnModel();
    /**
       @assert (colModel.getColumnCount() == table.getColumnCount()), "table and column model don't agree";
    **/
    final int columns = colModel.getColumnCount();

    for(int columnIndex=0; columnIndex<columns; columnIndex++) {
      int maxWidth = 0;
      final TableColumn column = colModel.getColumn(columnIndex);
      /**
         @assert (column != null)
      **/
      TableCellRenderer renderer = column.getCellRenderer();
      
      if(renderer == null) {
        renderer = table.getDefaultRenderer(table.getColumnClass(columnIndex));
      }
      if(renderer != null) {
        final int rows = table.getRowCount();
        for(int rowIndex=0; rowIndex<rows; rowIndex++) {
          final Object object = table.getValueAt(rowIndex, columnIndex);
          maxWidth =
            Math.max(maxWidth,
                     renderer.getTableCellRendererComponent(table,
                                                            object,
                                                            false,
                                                            false,
                                                            rowIndex,
                                                            columnIndex).getPreferredSize().width);
        }
        //?????
        //maxWidth = Math.max(maxWidth, column.getWidth());
        
        renderer = column.getHeaderRenderer();
        if(renderer == null) {
          renderer = table.getTableHeader().getDefaultRenderer();
        }
        maxWidth = Math.max(maxWidth,
                            renderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, -1, columnIndex).getPreferredSize().width);
        column.setMinWidth(maxWidth + 5);
      }
    }
  }
}
