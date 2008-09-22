/*
 * Copyright (c) 2008
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
package net.mtu.eggplant.io;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Writer that outputs to log4j.
 */
public class Log4jWriter extends Writer {

  private final Logger logger;

  private final Level level;

  private final StringBuilder text = new StringBuilder();

  /**
   * @param logger the logger to log to
   * @param level the level to log at
   */
  public Log4jWriter(final Logger logger, final Level level) {
    this.logger = logger;
    this.level = level;
  }

  /**
   * @param lock the lock
   * @param logger the logger to log to
   * @param level the level to log at
   */
  public Log4jWriter(final Object lock, final Logger logger, final Level level) {
    super(lock);
    this.logger = logger;
    this.level = level;
  }

  /**
   * @see java.io.Writer#close()
   */
  @Override
  public void close() throws IOException {
    flush();
  }

  /**
   * @see java.io.Writer#flush()
   */
  @Override
  public void flush() throws IOException {
    final String s = text.toString();
    text.setLength(0);
    if(s.length() > 0) {
      this.logger.log(this.level, s);
    }
  }

  /**
   * @see java.io.Writer#write(char[], int, int)
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    text.append(cbuf, off, len);
  }

}
