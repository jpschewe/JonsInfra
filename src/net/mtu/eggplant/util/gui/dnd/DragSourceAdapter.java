/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.gui.dnd;

import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceEvent;
  
/**
   Empty implementation of DragSourceListener.
**/
public class DragSourceAdapter implements DragSourceListener {
  public void dragDropEnd(final DragSourceDropEvent dsde) { }
  public void dragEnter(final DragSourceDragEvent dsde) { }
  public void dragExit(final DragSourceEvent dse) { }
  public void dragOver(final DragSourceDragEvent dsde) { }
  public void dropActionChanged(final DragSourceDragEvent dsde) { }

}
