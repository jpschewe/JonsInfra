/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
  
package org.tcfreenet.schewe;

import java.sql.*;

public class Testsql {
  public static void main(String[] args) {
    Connection connection = null;
    
    //String url = "jdbc:rst://localhost/mydb";    
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

    try {
      DatabaseMetaData dbmd = connection.getMetaData();
      ResultSet dbrs = dbmd.getTables(null, null, "%", null);
      ResultSetMetaData md = dbrs.getMetaData();
      for(int i=1; i<=md.getColumnCount(); i++) {
        System.out.print(md.getColumnName(i) + " | ");
      }
      System.out.println();
      while(dbrs.next()) {
        for(int i=1; i<=md.getColumnCount(); i++) {
          System.out.print(dbrs.getString(i) + " | ");
        }
        System.out.println();
      }
    }
    catch(SQLException sqle) {
      System.err.println("Caught error executing query " + sqle);
      System.exit(1);
    }
    //System.exit(0);
    
    Statement st = null;
    try {
      st = connection.createStatement();
    }
    catch(SQLException sqle) {
      System.err.println("caught error creating statement: " + sqle);
      System.exit(1);
    }

    String query = "select * from addressbook";
    try {
      ResultSet rs = st.executeQuery(query);
      ResultSetMetaData md = rs.getMetaData();
      for(int i=1; i<=md.getColumnCount(); i++) {
        System.out.print(":" + md.getColumnName(i) + ": | ");
      }
      System.out.println();
      while(rs.next()) {
        for(int i=1; i<=md.getColumnCount(); i++) {
          System.out.print(rs.getString(i) + " | ");
        }
        System.out.println();
      }
    }
    catch(SQLException sqle) {
      System.err.println("Caught error " + sqle);
    }
    
  }
  
}
