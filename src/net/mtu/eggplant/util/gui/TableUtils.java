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

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * utilities for working with tables
 * 
 * @version $Revision: 1.5 $
 */
public final class TableUtils {

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
