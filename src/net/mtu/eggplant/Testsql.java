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
package net.mtu.eggplant;

import java.sql.*;

/**
 * @version $Revision: 1.4 $
 */
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
