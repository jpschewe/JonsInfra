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
package net.mtu.eggplant.util;

import java.io.PrintStream;

/**
   This example is from the book _Java in a Nutshell_ by David Flanagan.
   Written by David Flanagan.  Copyright (c) 1996 O'Reilly & Associates.
   You may study, use, modify, and distribute this example for any purpose.
   This example is provided WITHOUT WARRANTY either expressed or implied.
**/
public class ThreadLister {
  
  /**
     Display info about a thread.
     @param out the stream to print stuff on
     @param t the thread to get info on
     @param indent how much to indent
  **/
  public static void print_thread_info(PrintStream out, Thread t, 
                                       String indent) {
    if (t == null) return;
    out.println(indent + "Thread: " + t.getName() +
                "  Priority: " + t.getPriority() +
                (t.isDaemon()?" Daemon":"") +
                (t.isAlive()?"":" Not Alive"));
  }
    
  /**
     Display info about a thread group and its threads and groups
     @param out the stream to print on
     @param g the thread group
     @param indent how much to indent
  **/
  public static void list_group(PrintStream out, ThreadGroup g, 
                                String indent) {
    if (g == null) return;
    int num_threads = g.activeCount();
    int num_groups = g.activeGroupCount();
    Thread[] threads = new Thread[num_threads];
    ThreadGroup[] groups = new ThreadGroup[num_groups];
        
    g.enumerate(threads, false);
    g.enumerate(groups, false);
        
    out.println(indent + "Thread Group: " + g.getName() + 
                "  Max Priority: " + g.getMaxPriority() +
                (g.isDaemon()?" Daemon":""));
        
    for(int i = 0; i < num_threads; i++)
      print_thread_info(out, threads[i], indent + "    ");
    for(int i = 0; i < num_groups; i++)
      list_group(out, groups[i], indent + "    ");
  }
    
  /**
     Find the root thread group and list it recursively
     @param out the stream to print on
  **/
  public static void listAllThreads(PrintStream out) {
    ThreadGroup current_thread_group;
    ThreadGroup root_thread_group;
    ThreadGroup parent;
        
    // Get the current thread group
    current_thread_group = Thread.currentThread().getThreadGroup();
        
    // Now go find the root thread group
    root_thread_group = current_thread_group;
    parent = root_thread_group.getParent();
    while(parent != null) {
      root_thread_group = parent;
      parent = parent.getParent();
    }
        
    // And list it, recursively
    list_group(out, root_thread_group, "");
  }
    
    
  public static void main(String[] args) {
    ThreadLister.listAllThreads(System.out);
  }
}
