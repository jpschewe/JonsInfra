;;This file assumes that the following variables and methods exist:
(jde-set-project-name "infra-project")
(let ((project-root (file-name-directory load-file-name)))
  (jde-set-variables
   '(jde-run-working-directory (expand-file-name "src/" project-root))
   '(jde-run-read-app-args t)
   '(jde-global-classpath (list
			   (expand-file-name "src/" project-root)
			   (expand-file-name "lib/junit-3.8.jar" project-root)
			   (expand-file-name "jre/lib/rt.jar" java-home)
			   (expand-file-name "lib/tools.jar" java-home)
			   ))

   '(jde-compile-option-deprecation t)
   '(jde-run-option-vm-args '("-DASSERT_BEHAVIOR=CONTINUE "))
   )
  )