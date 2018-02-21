pipeline {
    agent any
    stages {
        stage('Non-Parallel Stage') {
            steps {
                echo 'This stage will be executed first.'
            }
        }
        stage('Parallel Stage') {
            when {
                branch 'gradle'
            }
            failFast true
            parallel {
                stage('Chrome'){
                    parallel{
                        stage('Chrome 1') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Chrome 1"
                            }
                        }
                        stage('Chrome 2') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Chrome 2"
                            }
                        }
                        stage('Chrome 3') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Chrome 3"
                            }
                        }
                        stage('Chrome 4') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Chrome 4"
                            }
                        }
                    }
                }
                stage('Firefox'){
                    parallel{
                        stage('Firefox 1') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Firefox 1"
                            }
                        }
                        stage('Firefox 2') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Firefox 2"
                            }
                        }
                        stage('Firefox 3') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Firefox 3"
                            }
                        }
                        stage('Firefox 4') {
                            agent {
                                label "Selenium"
                            }
                            steps {
                                echo "Firefox 4"
                            }
                        }
                    }
                }
            }
            stage('generate results'){
                agent {
                    label "master"
                }
                steps {
                    echo "Get Results"
                }
            }
        }
    }
}
