;;This file assumes that the following variables and methods exist:
;;home-root - root of home project
;;java-vm - jvm to use
;;java-home - root of java install directory
(jde-set-project-name "basic-java-project")
(jde-set-variables
 '(jde-run-working-directory (expand-file-name home-root))
 '(jde-run-read-app-args t)
 '(jde-global-classpath (list
			 (expand-file-name home-root)
			 (expand-file-name "lib/jlfgr-1_0.jar" home-root)
			 (expand-file-name "lib/postgres-jdbc6.5-1.2.jar" home-root)
			 (expand-file-name "lib/RmiJdbc.jar" home-root)
			 (expand-file-name "lib/activation.jar" home-root)
			 (expand-file-name "lib/antlr.jar" home-root)
			 (expand-file-name "lib/jaf10.zip" home-root)
			 (expand-file-name "lib/junit.jar" home-root)
			 (expand-file-name "lib/mail.jar" home-root)
			 (expand-file-name "lib/java-getopt-1.0.7.jar" home-root)
			 (expand-file-name "jre/lib/rt.jar" java-home)
			 ))

 '(jde-compile-option-deprecation t)
 '(jde-run-java-vm java-vm)
 '(jde-run-option-vm-args '("-DASSERT_BEHAVIOR=CONTINUE "));-Djava.rmi.server.codebase=http://mn65-eggplant/applet/ -Djava.rmi.server.hostname=mn65-eggplant.htc.honeywell.com")); -Djava.library.path=d:\\jschewe\\projects\\IPSO\\Active-R1\\com\\honywell\\goalsetter\\bpc;d:\\jschewe\\projects\\IPSO\\Active-R1\\com\\honeywell\\goalsetter\\bpc\\bpc_ellipsoid"))
 )
