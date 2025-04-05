pipeline {
    agent any

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
            steps {
                git credentialsId: 'a6728bc9-9c97-49b7-854c-268fd3eb5c64',
                    url: 'https://github.com/shivusrd/Combined-NewMaven'
            }
        }
        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean compile'
                    } else {
                        bat 'mvn clean compile'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    def testngXml = params.TESTNG_XML
                    echo "TESTNG_XML parameter value: ${testngXml}" // For debugging
                    def mvnCommand = "mvn test -DtestngFile=${testngXml} -Dbrowser=${params.BROWSER} -Durl=${params.URL} -DcaptureScreenshots=${params.CAPTURE_SCREENSHOTS} -DenableLogs=${params.ENABLE_LOGS} -DenableExtentReports=${params.ENABLE_EXTENT_REPORTS}"

                    if (isUnix()) {
                        sh mvnCommand
                    } else {
                        bat mvnCommand
                    }
                }
                // No 'testng' step as we are only using Extent Reports
            }
        }
        stage('Post-build Actions') {
            steps {
                archiveArtifacts 'reports/ExtentReport.html' // Still good practice to archive
                publishHTML([allowMissing: false,
                             alwaysLinkToLastBuild: true, // Link to the report of the last build
                             keepAll: false,
                             reportDir: 'reports', // The directory containing your HTML report in the workspace
                             reportFiles: 'ExtentReport.html',
                             reportName: 'Extent Report'])
            }
        }
    }
}
