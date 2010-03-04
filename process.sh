#!/bin/bash

OPWD="${PWD}"
mypath=`dirname $0`
cd "${mypath}"
mypath="${PWD}"
cd "${OPWD}"

fll_sw=/home/jpschewe/projects/fll-sw/working-dir/scoring
fll_lib=${fll_sw}/lib
tomcat_src=${fll_sw}/tools/tomcat

fll_classpath=\
${tomcat_src}/lib/servlet-api.jar:\
${tomcat_src}/lib/jsp-api.jar:\
${fll_lib}/commons-fileupload-1.1.1.jar:\
${fll_lib}/commons-io-1.3.jar:\
${fll_lib}/commons-lang-2.0.jar:\
${fll_lib}/hsqldb-1.8.0.7.jar:\
${fll_lib}/iText-5.0.1.jar:\
${fll_lib}/jlfgr-1_0.jar:\
${fll_lib}/JonsInfra-0.8.jar:\
${fll_lib}/jstl.jar:\
${fll_lib}/log4j-1.2.14.jar:\
${fll_lib}/log4jdbc3-1.1.jar:\
${fll_lib}/opencsv-2.1-jps.jar:\
${fll_lib}/poi-3.6-20091214.jar:\
${fll_lib}/poi-ooxml-3.6-20091214.jar:\
${fll_lib}/poi-ooxml-schemas-3.6-20091214.jar:\
${fll_lib}/serializer.jar:\
${fll_lib}/slf4j-api-1.5.3.jar:\
${fll_lib}/slf4j-log4j12-1.5.3.jar:\
${fll_lib}/standard.jar:\
${fll_lib}/xalan.jar:\
${fll_lib}/xercesImpl.jar:\
${fll_lib}/xml-apis.jar:\
${fll_lib}/xmlbeans-2.3.0.jar:\
${fll_lib}/xmlunit-1.2.jar:\
${fll_lib}/findbugs-1.3.9/lib/annotations.jar:\
${fll_lib}/findbugs-1.3.9/lib/jsr305.jar:\
${fll_sw}/build/tomcat/webapps/fll-sw/WEB-INF/classes

apt -nocompile \
  -factorypath ${mypath}/build/classes/main:${mypath}/lib/slf4j-api-1.5.3.jar:${mypath}/lib/findbugs-1.3.9/lib/annotations.jar:${mypath}/lib/findbugs-1.3.9/lib/jsr305.jar \
  -classpath ${fll_classpath} \
  -factory net.mtu.eggplant.annotation.FindBugsAnnotationProcessorFactory \
  `find ${fll_sw}/src -type f -name '*.java'`
  
