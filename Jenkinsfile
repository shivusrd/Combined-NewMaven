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
        stage('Build and Test (Inside Docker)') {
            agent {
                docker {
                    image 'my-jenkins-dind' // Run this stage inside your custom Jenkins image with Docker
                    alwaysPull false // Only pull if the image is not present
                    reuseNode true // Keep the agent node for subsequent steps in this stage
                }
            }
            steps {
                sh 'docker --version' // Verify Docker is available
                sh 'mvn --version'    // Verify Maven is available (should be in the base jenkins/maven image)
                sh "docker run --rm -v $(pwd):/app -w /app my-maven-test-image mvn clean install -B -DtestngFile=${params.TESTNG_XML} -Dbrowser=${params.BROWSER} -Durl=${params.URL} -DcaptureScreenshots=${params.CAPTURE_SCREENSHOTS} -DenableLogs=${params.ENABLE_LOGS} -DenableExtentReports=${params.ENABLE_EXTENT_REPORTS}"
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
