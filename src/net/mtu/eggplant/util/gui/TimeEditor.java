/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeEditor extends JPanel {
  public final static int HOUR = 1;
  public final static int MINUTE = 2;
  public final static int SECOND = 3;
  public final static int TIMESEPARATOR = 4;
  
  public TimeEditor() {
    
  }

  protected void createFields() {
    _hourText = new JTextField(4);
    _minuteText = new JTextField(2);
    _secondText = new JTextField(2);
  }
  
  protected void addFields(boolean hour, boolean minute, boolean second) {
    if(hour) {
      addField(HOUR);
      if(minute || second)
        addField(TIMESEPARATOR);
    }
    if(minute) {
      addField(MINUTE);
      if(second)
        addField(TIMESEPARATOR);
    }
    if(second) {
      addField(second);
    }
  }

  protected void addField(int field) {
    switch(field) {
    case SECOND:
      _internalPanel.add(_secondText);
      break;
    case MINUTE:
      _internalPanel.add(_minuteText);
      break;
    case HOUR:
      _internalPanel.add(_hourText);
      break;
    case TIMESEPARATOR:
      _internalPanel.add(new JLable(_timeSeparator));
      break;
    }
  }
  
  private String _timeSeparator = ":";
  private JTextField _secondText;
  private JTextField _minuteText;
  private JTextField _hourText;
  
}
