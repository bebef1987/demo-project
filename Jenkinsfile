pipeline {
  agent none
  stages {
    stage('One') {
      steps {
        echo 'Hello'
      }
    }
    stage('Evaluate Master') {
      when {
        branch 'master'
      }
      steps {
        echo 'World'
        echo 'Heal it'
      }
    }
    stage('Evaluate Gradle') {
      steps {
        echo 'World'
      }
    }
  }
}