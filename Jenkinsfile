pipeline {
  agent any
  stages {
    stage('Set Build Name') {
      steps {
        script {
          if (env.BRANCH_NAME.startsWith('PR')) {
            currentBuild.displayName = "#${env.BUILD_NUMBER} - ${env.CHANGE_BRANCH}"
          } else {
            currentBuild.displayName = "#${env.BUILD_NUMBER} - ${env.BRANCH_NAME}"
          }
        }
      }
    }
    stage("Prepare build environment") {
       steps {
			 /* Not relying on 'checkout scm' because it creates detached head.
             For release plugin we must work on actual branch (he makes commit).
             So it is better to checkout/pull now rather than before release - to be sure we
             make release commit for same sources that were used to make build.
          */
          cleanWs()
			 checkout scm
			 sh "git config --global user.name flanki-jenkins"
          sh "git config --global user.email jenkins@proszowski.eu"
          sh "export IS_PR=`echo $env.BRANCH_NAME | grep -o PR- | wc -l`"
          sh "export BRANCH_NAME=$(if [ ${IS_PR} -eq 0 ]; then echo $env.BRANCH_NAME; else echo $env.CHANGE_BRANCH; fi)"
          sh "git checkout ${BRANCH_NAME}"
          sh "git pull --ff-only"
       }
    }
	 stage("Test application") {
	 	steps{
	 		sh 'mvn clean install'
		}
	 }
  } 
}
