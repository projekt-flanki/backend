def pull_request = env.CHANGE_BRANCH != null
def should_deploy = env.BRANCH_NAME == 'master'

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
		 when {
		 	expression { pull_request == false }
		 }
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
          sh "git checkout ${env.BRANCH_NAME}"
          sh "git pull --ff-only"
       }
    }
    stage("Prepare build environment for pull request") {
		 when {
		 	expression { pull_request == true }
		 }
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
          sh "git pull --ff-only"
       }
    }
	 stage("Test application") {
	 	steps{
	 		sh 'mvn clean install'
		}
	 }
	 stage("Deploy application") {
		when {
			expression { should_deploy == true }
		}
	 	steps{
	 		sh "ssh -i ~/.ssh/id_rsa flanki@40.68.3.243 'cd backend; git pull; pkill java; nohup mvn spring-boot:run > foo.out 2> foo.err < /dev/null &'"
		}
	 }
  } 
}
