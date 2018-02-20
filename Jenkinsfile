pipeline {
  agent none
  stages {
    stage('One') {
      parallel {
        stage('One') {
          steps {
            echo 'Hello'
          }
        }
        stage('test1') {
          steps {
            load 'BrowserSettings/smoke-browsers.json'
          }
        }
      }
    }
    stage('Evaluate Master') {
      parallel {
        stage('Evaluate Master') {
          when {
            branch 'master'
          }
          steps {
            echo 'World'
            echo 'Heal it'
          }
        }
        stage('Step2 ') {
          steps {
            readJSON(file: 'BrowserSettings/smoke-browsers.json', text: 'File')
            node(label: 'Selenium')
          }
        }
      }
    }
  }
}