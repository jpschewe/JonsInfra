/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org

*/
package org.tcfreenet.schewe;

import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Convertsql {

  public static void main(String[] args) {
    String newAddressBook = "newAddressBook";
    
    Connection connection = null;
    String url = "jdbc:postgresql://localhost/mydb";
    try {
      //Class.forName("rst.sql.Driver");
      Class.forName("postgresql.Driver");
    } 
    catch(ClassNotFoundException cnfe) {
      System.err.println("Can't find the driver!");
    }
    try {
      connection = DriverManager.getConnection(url, "jpschewe", "");
    }
    catch(SQLException sqle) {
      System.err.println("Got exception " + sqle);
      System.exit(1);
    }
    
    Statement st = null;

    try {
      st = connection.createStatement();
    }
    catch(SQLException sqle) {
      System.err.println("caught error creating statement: " + sqle);
      System.exit(1);
    }

    try {
      st.execute("drop table " + newAddressBook);
    }
    catch(SQLException sqle) {
      System.err.println(sqle);
    }

    try {
      st.execute("create table " + newAddressBook + "(lastname varchar(24) not null,firstname varchar(24) not null,phone varchar(13),street varchar(40),city varchar(20),state varchar(2),zip varchar(10),email varchar(30),comments varchar(40),\"1998\" bool default false)");
    }
    catch(SQLException sqle) {
      System.err.println(sqle);
    }
    
    String query = "select * from addressBook";
    ResultSet rs = null;
    try {
      rs = st.executeQuery(query); // get everything
    }
    catch(SQLException sqle) {
      System.err.println("Caught error " + sqle);
      System.exit(1);
    }

    // loop, foreach row
    try {
      while(rs.next()) {
        String city = null;
        String state = null;
        String zip = null;
        String address2 = rs.getString("address2");
        if(rs.wasNull()) address2 = null;
        
        if(address2 != null) {
          address2 = address2.trim();          
          int commaIndex = address2.indexOf(',');
          if(commaIndex != -1) {
            char[] cityArray = new char[commaIndex];

            address2.getChars(0, commaIndex, cityArray, 0);
            city = new String(cityArray);
            city = city.trim();

            char[] stateZipArray = new char[address2.length() - commaIndex-1];
            address2.getChars(commaIndex+1, address2.length(), stateZipArray,
                              0);
            String stateZip = new String(stateZipArray);
            stateZip = stateZip.trim();
            
            int spaceIndex = stateZip.indexOf(' ');
            if(spaceIndex != -1) {
              char[] stateArray = new char[spaceIndex];
              stateZip.getChars(0, spaceIndex, stateArray, 0);
              state = new String(stateArray);
              state = state.trim();
            
              char[] zipArray = new char[stateZip.length() - spaceIndex];
              stateZip.getChars(spaceIndex, stateZip.length(), zipArray, 0);
              zip = new String(zipArray);
              zip = zip.trim();
            }
            else {
              char[] stateArray = new char[address2.length() - commaIndex - 1];
              address2.getChars(commaIndex+1, address2.length(), stateArray, 0);
              state = new String(stateArray);
            }
          }
        }
        
        String lastName = rs.getString("lastname");
        String firstName = rs.getString("firstname");
        String phone = rs.getString("phone");
        String street = rs.getString("address1");
        String email = rs.getString("email");
        String comments = rs.getString("comments");
        boolean xmas = rs.getBoolean("1998");
        // need to massage all ' to \' in all strings
        PreparedStatement prepSt =
          connection.prepareStatement("insert into " + newAddressBook + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        prepSt.setString(1, cleanString(lastName));
        prepSt.setString(2, cleanString(firstName));
        if(phone == null)
          prepSt.setNull(3, Types.VARCHAR);
        else
          prepSt.setString(3, cleanString(phone));
        if(street == null)
          prepSt.setNull(4, Types.VARCHAR);
        else
          prepSt.setString(4, cleanString(street));
        if(city == null)
          prepSt.setNull(5, Types.VARCHAR);
        else
          prepSt.setString(5, cleanString(city));
        if(state == null)
          prepSt.setNull(6, Types.VARCHAR);
        else
          prepSt.setString(6, cleanString(state));
        if(zip == null)
          prepSt.setNull(7, Types.VARCHAR);
        else
          prepSt.setString(7, cleanString(zip));
        if(email == null)
          prepSt.setNull(8, Types.VARCHAR);
        else
          prepSt.setString(8, cleanString(email));
        if(comments == null)
          prepSt.setNull(9, Types.VARCHAR);
        else
          prepSt.setString(9, cleanString(comments));

        prepSt.setBoolean(10, xmas);
        
        prepSt.executeUpdate();
      }
    }
    catch(SQLException sqle) {
      System.err.println("Caught sql error " + sqle);
    }

    try {
      st.close();
      connection.close();
    }
    catch(SQLException sqle) {
      System.out.println(sqle);
    }
  }

  public static String cleanString(String str) {
    if(str == null) return str;
    
    int index = str.indexOf('\'');
    while(index != -1) {
      StringBuffer strBuf = new StringBuffer(str);
      strBuf.insert(index, '\\');
      str = strBuf.toString();
      index = str.indexOf('\'', index+2);
    }
    return str;
  }
  
}

