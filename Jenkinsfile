pipeline {
  agent any
  stages {
    stage('Set Build Name') {
      steps {
        script {
          if (env.BRANCH_NAME.startsWith('PR')) {
            currentBuild.displayName = "#${env.BUILD_NUMBER} - ${env.CHANGE_BRANCH}"
				environment {
					BRANCH_NAME = $CHANGE_BRANCH
				}
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
          sh "git fetch"
			 script { 
			 	branchName = env.BRANCH_NAME
			 	if(env.CHANGE_BRANCH != null){
					branchName = env.CHANGE_BRANCH
				}
			 }
          sh "git checkout ${branchName}"
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
