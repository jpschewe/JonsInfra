/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.sql;


import org.tcfreenet.schewe.utils.StringPair;
import org.tcfreenet.schewe.utils.Debug;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JComboBox;
//import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

/**
   This dialog allows the user to fill in the parameters needed for a
   connection.  It is hardcoded to use either the standard jdbc to odbc driver 
   or to use the rmi driver.
**/
public class SQLConnectionDialog extends JDialog {
  
  public SQLConnectionDialog(java.awt.Frame parent, StringPair[] drivers) {
    super(parent, "Open JDBC Connection", true);
    _drivers = drivers;
    
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints gbc;
    setSize(300, 150);
    
    JLabel driverLabel = new JLabel("Driver: ");
    gbc = new GridBagConstraints();
    gbc.fill = gbc.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 0.0;
    getContentPane().add(driverLabel, gbc);
    
    _driverCombo = new JComboBox();
    // waiting for jdk1.2!
    //_driverModel = new DefaultComboBoxModel();
    for(int i=0; i<_drivers.length; i++) {
      _driverCombo.addItem(_drivers[i].getStringOne());
    }
    /*
      _driverCombo.addItem("Standard JDBC driver");
      _driverCombo.addItem("RMI JDBC driver");
      _driverCombo.setSelectedItem("Standard JDBC driver");
    */
    _driverCombo.setSelectedIndex(0);
    
    //_driverCombo.setModel(_driverModel);
    
    gbc = new GridBagConstraints();
    gbc.fill = gbc.NONE;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.gridwidth = gbc.REMAINDER;
    getContentPane().add(_driverCombo, gbc);
    
    JLabel dataSourceLabel = new JLabel("Data Source:");
    gbc = new GridBagConstraints();
    gbc.fill = gbc.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 0.0;
    getContentPane().add(dataSourceLabel, gbc);

    _dataSourceText = new JTextField(20);
    gbc = new GridBagConstraints();
    gbc.fill = gbc.NONE;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.gridwidth = gbc.REMAINDER;
    getContentPane().add(_dataSourceText, gbc);

    JLabel userLabel = new JLabel("Username:");
    gbc = new GridBagConstraints();
    gbc.fill = gbc.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 0.0;
    getContentPane().add(userLabel, gbc);
    
    _userText = new JTextField(20);
    gbc = new GridBagConstraints();
    gbc.fill = gbc.NONE;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.gridwidth = gbc.REMAINDER;
    getContentPane().add(_userText, gbc);

    JLabel passLabel = new JLabel("Password:");
    gbc = new GridBagConstraints();
    gbc.fill = gbc.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 0.0;
    getContentPane().add(passLabel, gbc);
    
    _passText = new JPasswordField(20);
    gbc = new GridBagConstraints();
    gbc.fill = gbc.NONE;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.gridwidth = gbc.REMAINDER;
    getContentPane().add(_passText, gbc);

    /*
      OkCancelApplyPanel ocap = new OkCancelApplyPanel(this, true, true, false);
      gbc = new GridBagConstraints();
      gbc.gridwidth = gbc.REMAINDER;
      gbc.weightx = 1.0;
      gbc.weighty = 0.0;
      gbc.fill = gbc.HORIZONTAL;
      getContentPane().add(ocap, gbc);
    */
  }

  /*
    public void applyButtonClicked(ActionEvent ae) { 
    depopulate();
    }

    public void okButtonClicked(ActionEvent ae) {
    depopulate();
    setVisible(false);
    }

    public void cancelButtonClicked(ActionEvent ae) {
    _connection = null;
    setVisible(false);
    }
  */
  
  /**
     pull the data out of the dialog and store it internally, then build the
     connection object
  **/
  public void depopulate() {
    // add the driver to the driver manager.

    String dataSource = _dataSourceText.getText();
    String url = "jdbc:odbc:" + dataSource;
    String pass = new String(_passText.getPassword());
    String user = _userText.getText();
    String connectString = null;
    StringPair sp = (StringPair)_driverCombo.getSelectedItem();
    
    try {
      Class.forName(sp.getStringTwo()).newInstance();
    }
    catch(Exception e) {
      System.err.println("Couldn't find driver: " + sp.getStringTwo());
      _connection = null;
      return;
    }
    connectString = url;

    try {
      Class.forName("RmiJdbc.RJDriver").newInstance();
    }
    catch(Exception e) {
      Debug.println("Couldn't find the driver");
      _connection = null;
      return;
    } 

    // get this from the dialog box
    String rmiHost = "//129.235.65.128";    
    connectString = "jdbc:rmi:" + rmiHost + "/" + url;
    /* generic
       connectString = jdbc:subprotocol:subname
       jdbc:???://host/db
       examples                              subprotocol subname
       jdbc:rst://host/db           postgre  rst      //host/db
       jdbc:rmi://host/jdbc:odbc:db rmi      rmi      //host/jdbc:odbc:db
       jdbc:odbc:db sun                      odbc     db
       Driver.acceptsUrl(connectString);
       DriverManager.getConnection(connectString, user, pass);
    */
      
    try {
      _connection = DriverManager.getConnection(connectString, user, pass);
    }
    catch(SQLException sqle) {
      Debug.println("caught sql error opening connection: " + sqle);
      _connection = null;
    }
  }
  
  /**
     get the connection from this dialog.
     @return the connection, <code>null</code> if there was an error
  **/
  public Connection getConnection() { return _connection; }

  private StringPair[] _drivers = null;
  protected Connection _connection = null;
  protected JTextField _userText;
  //protected DefaultComboBoxModel _driverModel;
  protected JComboBox _driverCombo;
  protected JPasswordField _passText;
  protected JTextField _dataSourceText;
}
