pipeline {
  agent any
  stages {
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
          sh "git checkout $env.BRANCH_NAME"
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
