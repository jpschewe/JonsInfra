/*
  This file is licensed through the GNU public License.  Please read it.
  Basically you can modify this code as you wish, but you need to distribute
  the source code for all applications that use this code.

  I'd appreciate comments/suggestions on the code schewe@tcfreenet.org
*/
package org.tcfreenet.schewe.utils.sql;

import java.sql.Types;

public class SQLFunctions {

  public static Class getClassForType(int type) {
    try {
      switch(type) {
      case Types.CHAR:
      case Types.LONGVARCHAR:
      case Types.VARCHAR:
        return Class.forName("java.lang.String");
      case Types.DECIMAL:        
      case Types.NUMERIC:
        return Class.forName("java.math.BigDecimal");
      case Types.BIT:
        return Class.forName("java.lang.Boolean");
      case Types.SMALLINT:
      case Types.TINYINT:      
      case Types.INTEGER:
        return Class.forName("java.lang.Integer");
      case Types.BIGINT:
        return Class.forName("java.lang.Long");
      case Types.REAL:
        return Class.forName("java.lang.Float");
      case Types.DOUBLE:
      case Types.FLOAT:
        return Class.forName("java.lang.Double");
      case Types.BINARY:
      case Types.LONGVARBINARY:
      case Types.VARBINARY:
        return (new byte[0]).getClass();
      case Types.DATE:
        return Class.forName("java.sql.Date");
      case Types.TIME:
        return Class.forName("java.sql.Time");
      case Types.TIMESTAMP:
        return Class.forName("java.sql.Timestamp");
      case Types.NULL:
        return Class.forName("java.lang.Void");
      case Types.OTHER:      
      default:
        return Class.forName("java.lang.Object");
      }
    }
    catch(ClassNotFoundException cnfe) {
      throw new RuntimeException("Can't find standard class type " + cnfe);
    }
  }
  
}
