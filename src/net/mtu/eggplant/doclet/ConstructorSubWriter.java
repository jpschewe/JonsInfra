/*
 * @(#)ConstructorSubWriter.java	1.17 00/02/02
 *
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


package net.mtu.eggplant.doclet;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.Tag;
import com.sun.javadoc.ThrowsTag;
import com.sun.tools.doclets.VisibleMemberMap;

import java.util.List;

/**
 *
 * @author Robert Field
 * @author Atul M Dambalkar
 */
public class ConstructorSubWriter extends ExecutableMemberSubWriter {

  protected boolean foundNonPublicMember = false;

  public ConstructorSubWriter(SubWriterHolderWriter writer, 
                              ClassDoc classdoc) {
    super(writer, classdoc);
    checkForNonPublicMembers(visibleMemberMap.getMembersFor(classdoc));
  }

  public ConstructorSubWriter(SubWriterHolderWriter writer) {
    super(writer);
  }

  public void printSummaryLabel(ClassDoc cd) {
    writer.boldText("doclet.Constructor_Summary"); 
  }

  public void printSummaryAnchor(ClassDoc cd) {
    writer.anchor("constructor_summary");
  }
    
  public void printInheritedSummaryAnchor(ClassDoc cd) {
  }   // no such
    
  public void printInheritedSummaryLabel(ClassDoc cd) {
    // no such
  }

  public int getMemberKind() {
    return VisibleMemberMap.CONSTRUCTORS;
  }

  protected void printSummaryType(ProgramElementDoc member) {
    if (foundNonPublicMember) {
      writer.printTypeSummaryHeader();
      if (member.isProtected()) {
        print("protected ");
      } else if (member.isPrivate()) {
        print("private ");
      } else if (member.isPublic()) {
        writer.space();
      } else {
        writer.printText("doclet.Package_private");
      }
      writer.printTypeSummaryFooter();
    }
  }

  protected void printTags(ProgramElementDoc member) {
    ParamTag[] params = ((ConstructorDoc)member).paramTags();
    ThrowsTag[] thrown = ((ConstructorDoc)member).throwsTags();
    Tag[] sinces = member.tags("since");
    SeeTag[] sees = member.seeTags();
    Tag[] pres = member.tags("pre");
    Tag[] posts = member.tags("post");
    if (params.length + thrown.length + sees.length + sinces.length > 0
        || pres.length > 0
        || posts.length > 0) {
      writer.dd();
      writer.dl();
      printParamTags(params);
      if(pres.length > 0) {
        writer.dt();
        writer.boldText("doclet.PreConditions");
        for(int i=0; i<pres.length; i++) {
          writer.dd();
          writer.print(pres[i].text());
        }
      }
      if(posts.length > 0) {
        writer.dt();
        writer.boldText("doclet.PostConditions");
        for(int i=0; i<posts.length; i++) {
          writer.dd();
          writer.print(posts[i].text());
        }
      }      
      printThrowsTags(thrown);
      writer.printSinceTag(member);
      writer.printSeeTags(member);
      writer.dlEnd();
      writer.ddEnd();
    }
  }
                               
  protected void printHeader(ClassDoc cd) {
    writer.anchor("constructor_detail");
    writer.printTableHeadingBackground(writer.
                                       getText("doclet.Constructor_Detail"));
  }

  protected void navSummaryLink() {
    printNavSummaryLink(classdoc, 
                        visibleMemberMap.getMembersFor(classdoc).size() > 0? true: false);
  } 

  protected void printNavSummaryLink(ClassDoc cd, boolean link) {
    if (link) {
      writer.printHyperLink("", "constructor_summary",
                            writer.getText("doclet.navConstructor"));
    } else {
      writer.printText("doclet.navConstructor");
    }
  }

  protected void printNavDetailLink(boolean link) {
    if (link) {
      writer.printHyperLink("", "constructor_detail",
                            writer.getText("doclet.navConstructor"));
    } else {
      writer.printText("doclet.navConstructor");
    } 
  }

  protected void checkForNonPublicMembers(List members) {
    for (int i = 0; i < members.size(); i++) {
      if (!foundNonPublicMember) {
        if (!((ProgramElementDoc)(members.get(i))).isPublic()) {
          foundNonPublicMember = true;
          break;
        }
      }
    }
  }
}  
    
    
