;;This file assumes that the following variables and methods exist:
(jde-set-project-name "infra")
(let ((project-root (file-name-directory load-file-name)))
  (jde-set-variables
   '(jde-run-working-directory (expand-file-name "build/" project-root))
   '(jde-run-read-app-args t)
   '(jde-global-classpath (list
			   (expand-file-name "build/classes" project-root)
			   (expand-file-name "lib/junit-3.8.jar" project-root)
			   (expand-file-name "lib/log4j-1.2.8.jar" project-root)
			   ))

   '(jde-compile-option-deprecation t)
   '(jde-run-option-vm-args '("-DASSERT_BEHAVIOR=CONTINUE "))
   '(jde-gen-class-buffer-template 
     (list 
      "(funcall jde-gen-boilerplate-function)"
      "(jde-gen-get-package-statement)"
      "'>\"import org.apache.log4j.Logger;\"'n"
      "\"/**\" '>'n"
      "\" * Add class comment here!\" '>'n"
      "\" *\" '>'n"
      "\" * @version $Revision: 1.11 $\" '>'n"
      "\" */\" '>'n'"
      "\"public class \"" 
      "(file-name-sans-extension (file-name-nondirectory buffer-file-name))" 
      "\" \" (jde-gen-get-extend-class)" 
      "\"{\" '>'n"
      "'>'n"
      "'>\"private static final Logger LOG = Logger.getLogger(\"(file-name-sans-extension (file-name-nondirectory buffer-file-name))\".class);\"'n"
      "'>'n"
      "\"public \"" 
      "(file-name-sans-extension (file-name-nondirectory buffer-file-name))" 
      "\"()\"" 
      "\" {\" '>'n" 
      "'>'n" 
      "\"}\" '>'n" 
      "\"}\" '>'n")) 
   ))
