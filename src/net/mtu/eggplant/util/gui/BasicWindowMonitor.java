package net.mtu.eggplant.util.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Window;

/**
   basic window adapter for use in mains for testing.

   @author Jon Schewe
   @since schedinfra 1.2
**/
public class BasicWindowMonitor extends WindowAdapter {

  /**
     dispose of the window and exit the application
     @param e the event
  **/
  public void windowClosing(WindowEvent e) {
    Window w = e.getWindow();
    w.setVisible(false);
    w.dispose();
    System.exit(0);
  }
}
