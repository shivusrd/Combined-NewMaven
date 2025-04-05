pipeline {
    agent none // Define agent per stage

    parameters {
        string(name: 'TESTNG_XML', defaultValue: 'XML/Bikewale.xml', description: 'Path to the TestNG XML file to execute')
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser parameter for the tests')
        string(name: 'URL', defaultValue: 'https://www.bikewale.com/', description: 'URL parameter for the tests')
        booleanParam(name: 'CAPTURE_SCREENSHOTS', defaultValue: true, description: 'Enable screenshot capturing')
        booleanParam(name: 'ENABLE_LOGS', defaultValue: true, description: 'Enable logging')
        booleanParam(name: 'ENABLE_EXTENT_REPORTS', defaultValue: true, description: 'Enable Extent Reports')
    }

    stages {
        stage('Checkout') {
            agent any
            steps {
                git credentialsId: 'a6728bc9-9c97-49b7-854c-268fd3eb5c64',
                    url: 'https://github.com/shivusrd/Combined-NewMaven'
            }
        }
        stage('Build and Test') {
            agent {
                docker {
                    image 'my-maven-test-image' // Use your custom Docker image
                }
            }
            steps {
                sh "mvn clean install -B" // Build and run tests inside the container
            }
        }
        stage('Post-build Actions') {
            agent any
            steps {
                archiveArtifacts 'reports/ExtentReport.html'
                publishHTML([allowMissing: false,
                             alwaysLinkToLastBuild: true,
                             keepAll: false,
                             reportDir: 'reports',
                             reportFiles: 'ExtentReport.html',
                             reportName: 'Extent Report'])
            }
        }
    }
}
