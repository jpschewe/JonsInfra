/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code jpschewe@eggplant.mtu.net
*/
package net.mtu.eggplant.util.gui;

import javax.swing.table.TableModel;

public interface SortableTableModel extends TableModel {

  /**
     Sort the table model on this column.  If this is the currently sorted
     column, reverse the sort.  Needs to fire a {@link
     javax.swing.event.TableEvent TableEvent} signaling that the table data
     has changed.

     @pre (column >= 0)
  **/
  public void sort(final int column);

  /**
     @return the index of the currently sorted column.
  **/
  public int getSortedColumn();

  /**
     @return if the current column is sorted in ascending or descending order.
  **/
  public boolean isAscending();
}
