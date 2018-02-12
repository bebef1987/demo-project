catchError {
//    stage ('Start Testing') {
    def browsers
    node('Master') {
        checkout scm
        browsers = readJSON file: 'BrowserSettings/browsers.json'
    }

    def batchSize = 1

    def parallelBrowsers = [:]
    for (browser in browsers.browsers) {
        def currentBrowser= browser
        parallelBrowsers["Browser-${currentBrowser.browser}"] = {
            stage("Run automated tests for ${currentBrowser.browser}") {
                runTests(currentBrowser, batchSize)
                aggregateResults(currentBrowser, batchSize)
            }
        }
    }
    // Run Parallel Browsers
    parallel parallelBrowsers
    aggregateMultipleResults(browsers, batchSize)
//    }
}

def aggregateMultipleResults(browsers, batchSize) {
    node('Master') {
        stage("Generating MultiBrowser results") {
            checkout scm

            // Clean workspace
            withEnv(["JAVA_HOME=${tool 'java8'}", "MAVEN_HOME=${tool 'M3'}"]) {
                configFileProvider([configFile(fileId: 'garmin-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    bat "${MAVEN_HOME}/bin/mvn --settings ${MAVEN_SETTINGS} clean"
                }
            }

            for (browser in browsers.browsers) {
                def currentBrowser = browser
                // get report data
                for (int i = 1; i <= batchSize; i++) {
                    unstash "Browser-${currentBrowser.browser} Batch-${i}"
                }
            }

            // generate final reports
            withEnv(["JAVA_HOME=${tool 'java8'}", "MAVEN_HOME=${tool 'M3'}"]) {
                configFileProvider([configFile(fileId: 'garmin-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    bat "${MAVEN_HOME}/bin/mvn --settings ${MAVEN_SETTINGS} serenity:aggregate"
                }
            }

            // Publish Junit results
            step([$class: 'JUnitResultArchiver', testResults: '**/target/site/serenity/SERENITY-JUNIT-*.xml', name: 'Serenity'])

            // Publish HTML reports
            publishHTML(target: [
                    reportName           : "MultiBrowser results",
                    reportDir            : 'target/site/serenity',
                    reportFiles          : 'index.html',
                    keepAll              : true,
                    alwaysLinkToLastBuild: true,
                    allowMissing         : false
            ])
        }
    }
}


def aggregateResults(browser, batchSize) {
    node('Master') {
        stage("Generating results") {
            checkout scm

            // Clean workspace
            withEnv(["JAVA_HOME=${tool 'java8'}", "MAVEN_HOME=${tool 'M3'}"]) {
                configFileProvider([configFile(fileId: 'garmin-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    bat "${MAVEN_HOME}/bin/mvn --settings ${MAVEN_SETTINGS} clean"
                }
            }

            // get report data
            for (int i = 1; i <= batchSize; i++) {
                unstash "Browser-${browser.browser} Batch-${i}"
            }

            // generate final reports
            withEnv(["JAVA_HOME=${tool 'java8'}", "MAVEN_HOME=${tool 'M3'}"]) {
                configFileProvider([configFile(fileId: 'garmin-maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    bat "${MAVEN_HOME}/bin/mvn --settings ${MAVEN_SETTINGS} serenity:aggregate"
                }
            }

            // Publish Junit results
            step([$class: 'JUnitResultArchiver', testResults: '**/target/site/serenity/SERENITY-JUNIT-*.xml', name: 'Serenity'])

            // Publish HTML reports
            publishHTML(target: [
                    reportName           : "${browser.browser} results",
                    reportDir            : 'target/site/serenity',
                    reportFiles          : 'index.html',
                    keepAll              : true,
                    alwaysLinkToLastBuild: true,
                    allowMissing         : false
            ])
        }
    }
}

def runTests(browser, batchSize) {

    /* Create dictionary to hold set of parallel test executions. */
    def testBatch = [:]

    for (int i = 1; i <= batchSize; i++) {
        def currentBatch = i

        /* Loop over each record in splits to prepare the testGroups that we'll run in parallel. */
        testBatch["Browser-${browser.browser} Batch-${currentBatch}"] = {
            node('Selenium') {

                stage("Run automated tests on ${browser.browser} for split: ${currentBatch} of ${batchSize}") {
                    checkout scm
                    try {

                        // Get BrowserStack Credentials
                        withCredentials([
                                usernamePassword(
                                        credentialsId: 'BrowserStackCredentials',
                                        passwordVariable: 'BrowserStackKey',
                                        usernameVariable: 'BrowserStackUser'
                                )]) {

                            def localIdentifier = UUID.randomUUID().toString()

                            // Run tests with BrowserStack
                            with_browser_stack (localIdentifier){

                                // Get Java Environment
                                withEnv(["JAVA_HOME=${tool 'java8'}", "GRADLE=${tool 'GRADLE'}"]) {


                                        // Run Maven Tests
                                        bat "${GRADLE}/bin/gradle clean test  " +

                                                // Serenity Settings
                                                "-Dserenity.public.url=${env.BUILD_URL}Serenity/ " +

                                                // WebDriver Settings
                                                "-Dwebdriver.driver=remote " +

                                                // BrowserStack Settings
                                                "-Dbrowserstack.url=http://${BrowserStackUser}:${BrowserStackKey}@hub.browserstack.com:80/wd/hub " +
                                                "-Dbrowserstack.local=true " +
                                                "-Dbrowserstack.localIdentifier=${localIdentifier} " +

                                                // Project Settings
                                                "-Dbrowserstack.project=${env.BUILD_TAG} " +
                                                "-Dbrowserstack.build=Build_${env.BUILD_NUMBER} " +

                                                // Browser Settings
                                                "-Dbrowserstack.browser=${browser.browser} " +
                                                "-Dbrowserstack.browser_version=${browser.browser_version} " +

                                                "-Dbrowserstack.os=${browser.os} " +
                                                "-Dbrowserstack.os_version=${browser.os_version} " +

                                                // Context (tags) settings
                                                "-Dcontext=${browser.browser} " +
                                                "-Dinjected.tags=browser:${browser.browser} " +

                                                // Batch Strategy Settings
                                                "-Dserenity.batch.strategy=DIVIDE_BY_TEST_COUNT " +
                                                "-Dserenity.batch.size=${batchSize} " +
                                                "-Dserenity.batch.number=${currentBatch} " +

                                                //Threads settings
                                                " -DtestForkCount=2C " +
                                                " -DtestThreadCount=10  " +

                                                // Retry settings
                                                " -Dtest.retry.count=2 "

//                                                " -Dserenity.dry.run=true "

                                }
                            }
                        }
                    }
                    catch (error) {
                        println(error)
                    }
                    finally {

                        // Stash report data
                        stash allowEmpty: true,
                                includes: 'target/site/serenity/**/*',
                                name: "Browser-${browser.browser} Batch-${currentBatch}",
                                useDefaultExcludes: false
                    }
                }
            }
        }
    }

    // Run Parallel Batches
    parallel testBatch
}

def with_browser_stack(localIdentifier, actions) {

    // get BrowserStack Local
    withEnv(["BROWSERSTACK_LOCAL=${tool 'BrowserStackLocal'}"]) {
        // Start the connection
        bat "${BROWSERSTACK_LOCAL}/BrowserstackLocal.exe --key ${BrowserStackKey}  --local-identifier ${localIdentifier} --log-file BrowserStackLocalLog.log --only-automate --daemon start"

        try {
            //Run tests with browserstack
            actions()
        }
        finally {
            // Stop the connection
            bat "${BROWSERSTACK_LOCAL}/BrowserstackLocal.exe --local-identifier ${localIdentifier} --daemon stop"
        }
    }
}