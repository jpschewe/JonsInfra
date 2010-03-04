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
package net.mtu.eggplant.annotation;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.AnnotationTypeElementDeclaration;
import com.sun.mirror.declaration.AnnotationValue;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.util.SourcePosition;

/**
 * @author jpschewe
 */
public class FindBugsAnnotationProcessor implements AnnotationProcessor {

  private static final Logger LOGGER = LoggerFactory
  .getLogger(FindBugsAnnotationProcessor.class);

  private AnnotationProcessorEnvironment environment;

  private AnnotationTypeDeclaration suppressWarningsDeclaration;

  public FindBugsAnnotationProcessor(final AnnotationProcessorEnvironment env) {
    environment = env;
    suppressWarningsDeclaration = (AnnotationTypeDeclaration) environment.getTypeDeclaration("edu.umd.cs.findbugs.annotations.SuppressWarnings");
  }

  /**
   * @see com.sun.mirror.apt.AnnotationProcessor#process()
   */
  public void process() {
    Collection<Declaration> declarations = environment.getDeclarationsAnnotatedWith(suppressWarningsDeclaration);
    for (Declaration declaration : declarations) {
      processAnnotations(declaration);
    }
  }

  private void processAnnotations(final Declaration declaration) {
    // Get all of the annotation usage for this declaration.
    // the annotation mirror is a reflection of what is in the source.
    final Collection<AnnotationMirror> annotations = declaration.getAnnotationMirrors();
    
    for (final AnnotationMirror mirror : annotations) {
      if (mirror.getAnnotationType().getDeclaration().equals(suppressWarningsDeclaration)) {
        final SourcePosition position = mirror.getPosition();
        final Map<AnnotationTypeElementDeclaration, AnnotationValue> values = mirror.getElementValues();

        LOGGER.info("Declaration: "
            + declaration.toString());
        LOGGER.info("Position: "
            + position);
        LOGGER.info("Values:");
        for (final Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> entry : values.entrySet()) {
          final AnnotationTypeElementDeclaration elemDecl = entry.getKey();
          final AnnotationValue value = entry.getValue();
          LOGGER.info("    "
              + elemDecl + "=" + value);
        }
      }
    }
  }

}
