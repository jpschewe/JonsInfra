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

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeEditor extends JPanel {
  public final static int HOUR = 1;
  public final static int MINUTE = 2;
  public final static int SECOND = 3;
  public final static int TIMESEPARATOR = 4;
  
  public TimeEditor() {
    
  }

//   protected void createFields() {
//     _hourText = new JTextField(4);
//     _minuteText = new JTextField(2);
//     _secondText = new JTextField(2);
//   }
  
//   protected void addFields(boolean hour, boolean minute, boolean second) {
//     if(hour) {
//       addField(HOUR);
//       if(minute || second)
//         addField(TIMESEPARATOR);
//     }
//     if(minute) {
//       addField(MINUTE);
//       if(second)
//         addField(TIMESEPARATOR);
//     }
//     if(second) {
//       addField(second);
//     }
//   }

//   protected void addField(int field) {
//     switch(field) {
//     case SECOND:
//       _internalPanel.add(_secondText);
//       break;
//     case MINUTE:
//       _internalPanel.add(_minuteText);
//       break;
//     case HOUR:
//       _internalPanel.add(_hourText);
//       break;
//     case TIMESEPARATOR:
//       _internalPanel.add(new JLable(_timeSeparator));
//       break;
//     }
//   }
  
//   private String _timeSeparator = ":";
//   private JTextField _secondText;
//   private JTextField _minuteText;
//   private JTextField _hourText;
  
}
