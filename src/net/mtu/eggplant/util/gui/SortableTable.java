/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package org.tcfreenet.schewe.utils.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.util.Enumeration;

public class SortableTable extends JTable {

  public SortableTable(final SortableTableModel tm) {
    super(tm);
    getTableHeader().addMouseListener(_columnListener);
    addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(final PropertyChangeEvent pce) {
        if(pce.getPropertyName().equals("tableHeader")) {
          final JTableHeader oldHeader = (JTableHeader)pce.getOldValue();
          final JTableHeader newHeader = (JTableHeader)pce.getNewValue();
          if(oldHeader != null) {
            oldHeader.removeMouseListener(_columnListener);
          }
          if(newHeader != null) {
            newHeader.addMouseListener(_columnListener);
          }
        }
      }
    });
  }

  /**
     Overriden to make sure that the table model set is a SortedTableModel.
     
     @throws IllegalArgumentException if tm is not an instanceof {@link SortedTableModel}
  **/
  public void setModel(final TableModel tm) {
    if(! (tm instanceof SortableTableModel) ) {
      throw new IllegalArgumentException("table model must be a SortableTableModel");
    }
    super.setModel(tm);
  }

    private MouseAdapter _columnListener = new MouseAdapter() {
    public void mouseClicked(final MouseEvent me) {
      final JTableHeader header = getTableHeader(); 
      final TableColumnModel colModel = getColumnModel();
      final int columnIndex = colModel.getColumnIndexAtX(me.getX());
      final int modelIndex = colModel.getColumn(columnIndex).getModelIndex();
      if(modelIndex < 0) {
        return;
      }
      
      final SortableTableModel sm = (SortableTableModel)getModel();
      sm.sort(modelIndex);
      final Enumeration columnIter = colModel.getColumns();
      final boolean ascending = sm.isAscending();
      final int sortedColumn = sm.getSortedColumn();
      for(int cindex = 0; columnIter.hasMoreElements(); cindex++) {
        final TableColumn column = (TableColumn)columnIter.nextElement();
        String columnName = sm.getColumnName(column.getModelIndex());
        if(cindex == sortedColumn) {
          columnName += ascending ? " ^" : " v";
        } 
        column.setHeaderValue(columnName);
      }
      
      header.repaint();
    }
  };

}
