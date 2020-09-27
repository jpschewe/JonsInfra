#!/usr/bin/env groovy

pipeline {

  options { 
    buildDiscarder(logRotator(numToKeepStr: '10')) 
  }

  agent any
  
  stages {
    stage('Init') {
      steps {
        echo "NODE_NAME = ${env.NODE_NAME}"
        echo "My branch is: ${env.BRANCH_NAME}"
        printEnv()
      }
    }
    
    stage('Build Checker') {
        steps {
            // setup local checker repository
            dir("checker") {
                checkout changelog: false, 
                    poll: false, 
                    scm: [$class: 'GitSCM', 
                        branches: [[name: 'refs/tags/checker-framework-3.6.1']], 
                        doGenerateSubmoduleConfigurations: false, 
                        extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'checker-framework']], 
                        submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/typetools/checker-framework.git']]]
            
                // my copy of the annotated jdk
                checkout changelog: false, 
                    poll: false, 
                    scm: [$class: 'GitSCM', 
                        branches: [[name: 'refs/heads/jps-dev']], 
                        doGenerateSubmoduleConfigurations: false, 
                        extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'jdk']], 
                        submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/jpschewe/checker-typetools-jdk']]]
                        
                // my copy of annotation-tools that handles nested gradle builds
                // This can go away once https://github.com/typetools/annotation-tools/pull/314 is merged
                checkout changelog: false, 
                    poll: false, 
                    scm: [$class: 'GitSCM', 
                        branches: [[name: 'refs/heads/master']], 
                        doGenerateSubmoduleConfigurations: false, 
                        extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'annotation-tools']], 
                        submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/jpschewe/annotation-tools']]]
                                               
                dir("checker-framework") {
                    callGradle("assemble")
                } // dir checker-framework

            } // dir checker
        } // steps
    } // stage

    stage('Duplicate Code Analysis') {
      steps { 
        callGradle('cpdCheck')
        recordIssues tool: cpd(pattern: 'build/reports/cpd/cpdCheck.xml')
      }     
    }

    stage('Count lines of code') {
      steps { 
        callGradle('sloccount')
        sloccountPublish pattern: 'build/reports/sloccount/cloc.xml'
      }
    }
    
    stage('Checkstyle analysis') {
      steps { 
        callGradle('checkstyleMain')
        callGradle('checkstyleTest')
        recordIssues tool: checkStyle(pattern: 'build/reports/checkstyle/*.xml')
      }
    }
   

    stage('Tests') {
      steps {
        // runs all of the test tasks
      	callGradle('cobertura')
        junit testResults: "build/test-results/*est/TEST-*.xml", keepLongStdio: true
        step $class: 'CoberturaPublisher', coberturaReportFile: 'build/reports/cobertura/coverage.xml'                
      }
    }

    stage('SpotBugs analysis') {
      steps { 
        callGradle('spotbugsMain')
        callGradle('spotbugsTest')
        recordIssues tool: spotBugs(pattern: 'build/reports/spotbugs/*.xml', useRankAsPriority: true), qualityGates: [[threshold: 1, type: 'TOTAL', unstable: true]]     
      }
    }
    
    stage('Distribution') {
      steps {
	    timestamps {
	      callGradle('distZip')
	    } // timestamps
      } // steps           
    } // build and test stage
    
    /*
    stage('Publish documentation') {
      steps {    
          publishHTML (target: [
            allowMissing: false,
            alwaysLinkToLastBuild: false,
            keepAll: false,
            reportDir: 'build/gen-documentation',
            reportFiles: 'index.html',
            reportName: 'Documentation'
          ])
      }
    }
    */
    
  } // stages
    
  post {
    always {
      archiveArtifacts artifacts: '*.log,screenshots/,build/reports/,build/distributions/'
                      
            
      recordIssues tool: taskScanner(includePattern: '**/*.java,**/*.jsp,**/*.jspf,**/*.xml', excludePattern: 'checkstyle*.xml', highTags: 'FIXME,HACK', normalTags: 'TODO')
            
      recordIssues tool: java()  

      recordIssues tool: javaDoc()
                                   
      emailext recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']], 
          to: 'jpschewe@mtu.net',
          subject: '${PROJECT_NAME} - Build # ${BUILD_NUMBER} - ${BUILD_STATUS}!', 
          body: '''${PROJECT_NAME} - Build # ${BUILD_NUMBER} - ${BUILD_STATUS}

Changes:
${CHANGES}


Failed Tests:
${FAILED_TESTS}

Check console output at ${BUILD_URL} to view the full results.

Find more details at: ${JENKINS_URL}
'''
                
    } // always
  } // post

} // pipeline

def callGradle(task) {
  def args='--no-daemon -Dtest.ignoreFailures=true'

  if (isUnix()) {
    sh script: "./gradlew ${args} ${task}"
  } else {
    bat script: "gradlew.bat ${args} ${task}"
  }
}

def printEnv() {
  echo "Environment Variables"
  if (isUnix()) {
    sh script: 'printenv'
  } else {
    bat script: 'set'
  }
  echo "end Environment Variables"
}
