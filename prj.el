;;This file assumes that the following variables and methods exist:
;;infra-root - root of project
;;java-vm - jvm to use
;;java-home - root of java install directory
(jde-set-project-name "basic-java-project")
(jde-set-variables
 '(jde-run-working-directory (expand-file-name "src/" infra-root))
 '(jde-run-read-app-args t)
 '(jde-global-classpath (list
			 (expand-file-name "src/" infra-root)
			 (expand-file-name "lib/junit.jar" infra-root)
			 (expand-file-name "jre/lib/rt.jar" java-home)
			 ))

 '(jde-compile-option-deprecation t)
 '(jde-run-java-vm java-vm)
 '(jde-run-option-vm-args '("-DASSERT_BEHAVIOR=CONTINUE "))
 )
