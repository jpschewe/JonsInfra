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
package net.mtu.eggplant.util.sql;

import java.sql.Types;

/**
 * Handy functions for SQL
 * 
 * @version $Revision: 1.6 $
 */
public final class SQLFunctions {

  private SQLFunctions() {
  }

  /**
   * Do simple mapping from an SQL type to a Java Class.
   */
  public static Class<?> getClassForType(final int type) {
    try {
      switch (type) {
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
    } catch (final ClassNotFoundException cnfe) {
      throw new RuntimeException("Can't find standard class type " + cnfe);
    }
  }

}
