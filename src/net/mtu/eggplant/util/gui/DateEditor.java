/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
/*
  TODO:
  fix am/pm increment
  modify check method to handle am/pm
  modify check method to check for what fields are visible
  test removing odd fields
*/
package org.tcfreenet.schewe.utils.gui;

import java.text.DateFormat;
import java.text.DecimalFormat;

import java.util.Calendar;
import java.util.Locale;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.UIManager;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicArrowButton;

public class DateEditor extends JPanel {

  public static void main(String[] args) {
    DateEditor de = new DateEditor(Locale.getDefault(),
                                   Calendar.getInstance(), false, true, true,
                                   true, true, true, false);
    GraphicsUtils.basicGUIMain(de, false);
  }
  
  
  public final static int SECOND = 1;
  public final static int MINUTE = 2;
  public final static int HOUR = 3;
  public final static int DAY = 4;
  public final static int MONTH = 5;
  public final static int YEAR = 6;
  public final static int DATESEPARATOR = 7;
  public final static int TIMESEPARATOR = 8;
  public final static int SPACER = 9;
  public final static int AMPM = 10;
  
  /**
     standard constructor
     @param locale the Locale to use
     @param cal the calendar to default the date to
     @param military <code>true</code> to use 24-hour clock,
     <code>false</code> to use 12 hour clock     
     @param month <code>true</code> show the month field
     @param day <code>true</code> show the day field
     @param year <code>true</code> show the year field
     @param hour <code>true</code> show the hour field
     @param minute <code>true</code> show the minute field
     @param second <code>true</code> show the second field
  **/
  public DateEditor(Locale locale, Calendar cal, boolean military,
                    boolean month, boolean day, boolean year, boolean hour,
                    boolean minute, boolean second) {
    super();
    _month = month;
    _day = day;
    _year = year;
    _hour = hour;
    _minute = minute;
    _second = second;
    _dateListener = new PropertyChangeSupport(this);
    _military = military;
    _twoFormat = new DecimalFormat();
    _twoFormat.setMinimumIntegerDigits(2);
    _twoFormat.setGroupingUsed(false);

    setLayout(new GridBagLayout());
    GridBagConstraints gbc;
    _internalPanel = new JPanel();
    _internalPanel.setBackground(Color.white);
    _internalPanel.setLayout(new BoxLayout(_internalPanel, BoxLayout.X_AXIS));
    gbc = new GridBagConstraints();
    gbc.fill = gbc.NONE;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.anchor = gbc.WEST;
    add(_internalPanel, gbc);
    gbc = new GridBagConstraints();
    gbc.fill = gbc.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 0.0;
    add(new JPanel(), gbc);
    
    createFields();
    
    addFields(locale);
    
    setCalendar(cal);    
    populate();
  }

  protected void createFields() {
    _monthText =new JTextField(2);
    _monthText.setBorder(BorderFactory.createEmptyBorder());
    _monthText.setHorizontalAlignment(JTextField.CENTER);
    _monthText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _monthText.setBackground(_textSelectedColor);
          _currentField = MONTH;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _monthText.setBackground(_textUnselectedColor);
          check(MONTH, _monthText.getText());
        }
      }
    });
    
    _dayText = new JTextField(2);
    _dayText.setBorder(BorderFactory.createEmptyBorder());
    _dayText.setHorizontalAlignment(JTextField.CENTER);
    _dayText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _dayText.setBackground(_textSelectedColor);
          _currentField = DAY;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _dayText.setBackground(_textUnselectedColor);
          check(DAY, _dayText.getText());
        }
      }
    });
    
    _yearText =new JTextField(4);
    _yearText.setBorder(BorderFactory.createEmptyBorder());
    _yearText.setHorizontalAlignment(JTextField.CENTER);
    _yearText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _yearText.setBackground(_textSelectedColor);
          _currentField = YEAR;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _yearText.setBackground(_textUnselectedColor);
          check(YEAR, _yearText.getText());
        }
      }
    });
    
    _hourText = new JTextField(2);
    _hourText.setBorder(BorderFactory.createEmptyBorder());
    _hourText.setHorizontalAlignment(JTextField.CENTER);
    _hourText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _hourText.setBackground(_textSelectedColor);
          _currentField = HOUR;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _hourText.setBackground(_textUnselectedColor);
          check(HOUR, _hourText.getText());
        }
      }
    });
    
    _minuteText = new JTextField(2);
    _minuteText.setBorder(BorderFactory.createEmptyBorder());
    _minuteText.setHorizontalAlignment(JTextField.CENTER);
    _minuteText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _minuteText.setBackground(_textSelectedColor);
          _currentField = MINUTE;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _minuteText.setBackground(_textUnselectedColor);
          check(MINUTE, _minuteText.getText());
        }
      }
    });
    
    _secondText = new JTextField(2);
    _secondText.setBorder(BorderFactory.createEmptyBorder());
    _secondText.setHorizontalAlignment(JTextField.CENTER);
    _secondText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _secondText.setBackground(_textSelectedColor);
          _currentField = SECOND;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _secondText.setBackground(_textUnselectedColor);
          check(SECOND, _secondText.getText());
        }
      }
    });
    
    _ampmText = new JTextField(2);
    _ampmText.setBorder(BorderFactory.createEmptyBorder());
    _ampmText.setHorizontalAlignment(JTextField.CENTER);
    _ampmText.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _ampmText.setBackground(_textSelectedColor);
          _currentField = AMPM;
        }
      }
      public void focusLost(FocusEvent fe) {
        if(!fe.isTemporary()) {
          _ampmText.setBackground(_textUnselectedColor);
          check(AMPM, _ampmText.getText());
        }
      }
    });
    
  }

  protected void addFields(Locale locale) {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
    // not sure here, just assume US    
    boolean europe = false;
    // also take care of _dateSeparator and _timeSeparator

    if(europe) {
      if(_day) {
        addField(DAY);
        if(_month || _year)
          addField(DATESEPARATOR);
      }
      if(_month) {
        addField(MONTH);
        if(_year)
          addField(DATESEPARATOR);        
      }
    }
    else {
      if(_month) {
        addField(MONTH);
        if(_day || _year)
          addField(DATESEPARATOR);        
      }
      if(_day) {
        addField(DAY);
        if(_year)
          addField(DATESEPARATOR);        
      }
    }
    if(_year) {
      addField(YEAR);
      if(_hour || _minute || _second)
        addField(SPACER);
    }
    if(_hour) {
      addField(HOUR);
      if(_minute || _second)
        addField(TIMESEPARATOR);
    }
    if(_minute) {
      addField(MINUTE);
      if(_second)
        addField(TIMESEPARATOR);
    }
    if(_second) {
      addField(SECOND);
    }
    if(!_military)
      addField(AMPM);
    
    // add the buttons
    JPanel buttonBox = new JPanel();
    buttonBox.setLayout(new BoxLayout(buttonBox, BoxLayout.Y_AXIS));
    BasicArrowButton upArrow = new BasicArrowButton(BasicArrowButton.NORTH);
    upArrow.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        increment(_currentField, 1);
      }
    });
    buttonBox.add(upArrow);
    BasicArrowButton downArrow = new BasicArrowButton(BasicArrowButton.SOUTH);
    downArrow.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        increment(_currentField, -1);
      }
    });
    buttonBox.add(downArrow);
    
    _internalPanel.add(buttonBox);
    
  }
  
  public Calendar getCalendar() {
    return (Calendar)_currentCalendar.clone();
  }

  public void setCalendar(Calendar cal) {
    Calendar old = (Calendar)_currentCalendar.clone();
    _currentCalendar = (Calendar)cal.clone();
    fireDateChange(old, (Calendar)_currentCalendar.clone());
    populate();
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
    case DAY:
      _internalPanel.add(_dayText);
      break;
    case MONTH:
      _internalPanel.add(_monthText);
      break;
    case YEAR:
      _internalPanel.add(_yearText);
      break;
    case SPACER:
      _internalPanel.add(Box.createHorizontalStrut(3));
      break;
    case TIMESEPARATOR:
      _internalPanel.add(new JLabel(_timeSeparator, JLabel.CENTER));
      break;
    case DATESEPARATOR:
      _internalPanel.add(new JLabel(_dateSeparator, JLabel.CENTER));
      break;
    case AMPM:
      _internalPanel.add(_ampmText);
      break;
    }
  }

  /**
     refresh the display with the internal calendar
  **/
  public void populate() {
    long offset = 0;
    long value = _currentCalendar.get(Calendar.YEAR);
    if(_year) {
      _yearText.setText(_twoFormat.format(value));
    }
    else {
      offset = value * 12; // to months
      System.out.println("months offset " + offset);
      _monthText.setColumns(8);
    }
    value = _currentCalendar.get(Calendar.MONTH) + offset;
    if(_month) {
      _monthText.setText(_twoFormat.format(value+1));
      offset = 0;
    }
    else {
      Calendar foo = (Calendar)_currentCalendar.clone();
      foo.set(Calendar.DATE, 1);
      // convert to days      
      offset = Math.round(offset * 365.25) + foo.get(Calendar.DAY_OF_YEAR) - 1;
      System.out.println("days offset " + offset);
      _dayText.setColumns(8);
    }
    value = offset + _currentCalendar.get(Calendar.DATE);
    if(_day) {
      _dayText.setText(_twoFormat.format(value));
      offset = 0;
    }
    else {
      offset *= 24;
      System.out.println("hours offset " + offset);
      //offset = (offset + _currentCalendar.get(Calendar.DATE)) * 24; // to hours
      _hourText.setColumns(8);
    }
    if(_military) {
      value = _currentCalendar.get(Calendar.HOUR_OF_DAY) + offset;
    }
    else {
      value = _currentCalendar.get(Calendar.HOUR) + offset;
    }
    if(_hour) {
      _hourText.setText(_twoFormat.format(value));
      offset = 0;
    }
    else {
      offset += _currentCalendar.get(Calendar.HOUR_OF_DAY) * 60; // to minutes
      _minuteText.setColumns(8);
    }
    value = _currentCalendar.get(Calendar.MINUTE) + offset;
    if(_minute) {
      _minuteText.setText(_twoFormat.format(value));
      offset = 0;
    }
    else {
      offset += _currentCalendar.get(Calendar.MINUTE) * 60; // to seconds
      _secondText.setColumns(8);
    }
    value = _currentCalendar.get(Calendar.SECOND) + offset;
    if(_second) {
      _secondText.setText(_twoFormat.format(value));
      offset = 0;
    }
    // don't care about offset anymore

    if(!_military)
      _ampmText.setText(_currentCalendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
  }

  public void increment(int field, int amount) {
    Object old = _currentCalendar.clone();
    switch(field) {
    case SECOND:
      _currentCalendar.add(Calendar.SECOND, amount);
      break;
    case MINUTE:
      _currentCalendar.add(Calendar.MINUTE, amount);
      break;
    case HOUR:
      _currentCalendar.add(Calendar.HOUR_OF_DAY, amount);
      break;
    case DAY:
      _currentCalendar.add(Calendar.DATE, amount);
      break;
    case MONTH:
      _currentCalendar.add(Calendar.MONTH, amount);
      break;
    case YEAR:
      _currentCalendar.add(Calendar.YEAR, amount);
      break;
    case AMPM:
      if(Math.IEEEremainder(amount, 2) != 0) {
        if(_currentCalendar.get(Calendar.AM_PM) == Calendar.AM) {
          _currentCalendar.add(Calendar.HOUR_OF_DAY, 12);
        }
        else {
          _currentCalendar.add(Calendar.HOUR_OF_DAY, -12);
        }
      }
      break;
    }
    populate();
    fireDateChange((Calendar)old, _currentCalendar);
  }

  protected boolean check(int field, String text) {
    if(field == AMPM) {
      // special case
    }
    int value;
    try {
      value = Integer.parseInt(text);
    }
    catch(NumberFormatException nfe) {
      populate();
      return false;
    }
    switch(field) {
    case SECOND:
      _currentCalendar.set(Calendar.SECOND, value);
      break;
    case MINUTE:
      _currentCalendar.set(Calendar.MINUTE, value);
      break;
    case HOUR:
      if(_military)
         _currentCalendar.set(Calendar.HOUR_OF_DAY, value);
      else
        _currentCalendar.set(Calendar.HOUR, value);
      break;
    case DAY:
      _currentCalendar.set(Calendar.DATE, value);
      break;
    case MONTH:
      _currentCalendar.set(Calendar.MONTH, value);
      break;
    case YEAR:
      _currentCalendar.set(Calendar.YEAR, value);
      break;
    default:
      populate();
      return false;
    }
    return true;
  }

  protected void fireDateChange(Calendar old, Calendar clone) {
    _dateListener.firePropertyChange("date", old, clone);
  }
  
  public void addDateListener(PropertyChangeListener l) {
    _dateListener.addPropertyChangeListener(l);
  }

  public void removeDateListener(PropertyChangeListener l) {
    _dateListener.removePropertyChangeListener(l);
  }

  private JPanel _internalPanel;
  private Calendar _currentCalendar = Calendar.getInstance();
  private boolean _military;
  private PropertyChangeSupport _dateListener;
  private String _dateSeparator = "/";
  private String _timeSeparator = ":";
  private JTextField _monthText;
  private JTextField _dayText;
  private JTextField _hourText;
  private JTextField _minuteText;
  private JTextField _secondText;
  private JTextField _yearText;
  private boolean _hour = true;
  private boolean _minute = true;
  private boolean _second = true;
  private boolean _month = true;
  private boolean _day = true;
  private boolean _year = true;
  private DecimalFormat _twoFormat;
  private JTextField _ampmText;
  private int _currentField = 0;
  private static Color _textSelectedColor =
    UIManager.getColor("TextField.selectionBackground");
  private static Color _textUnselectedColor =
    UIManager.getColor("TextField.background");
}