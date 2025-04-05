pipeline {
    agent none

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
                git credentialsId: 'd8f42486-d764-426f-92ed-636d5a803bde',
                    url: 'https://github.com/shivusrd/Combined-NewMaven'
            }
        }
        stage('Build and Test (Java)') {
            agent {
                docker {
                    image 'maven:3.9.6-jdk-17'
                    alwaysPull false // Only pull if the image is not present locally
                }
            }
            steps {
                sh 'mvn clean install -B'
            }
            post {
                always {
                    cleanWs() // Clean the workspace after the stage
                }
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'maven:3.9.6-jdk-17'
                    alwaysPull false // Only pull if the image is not present locally
                }
            }
            steps {
                script {
                    def testngXml = params.TESTNG_XML
                    echo "TESTNG_XML parameter value: ${testngXml}"
                    def mvnCommand = "mvn test -DtestngFile=${testngXml} -Dbrowser=${params.BROWSER} -Durl=${params.URL} -DcaptureScreenshots=${params.CAPTURE_SCREENSHOTS} -DenableLogs=${params.ENABLE_LOGS} -DenableExtentReports=${params.ENABLE_EXTENT_REPORTS}"
                    sh mvnCommand
                }
            }
            post {
                always {
                    cleanWs() // Clean the workspace after the stage
                }
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