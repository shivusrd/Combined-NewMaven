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
        stage('Build and Test') {
            tools {
                maven 'MAVEN_HOME' // Use the Maven tool configured in Global Tool Configuration
            }
            steps {
                script {
                    def testngXml = params.TESTNG_XML
                    echo "TESTNG_XML parameter value: ${testngXml}"
                    def mvnCommand = "mvn clean test -DtestngFile=${testngXml} -Dbrowser=${params.BROWSER} -Durl=${params.URL} -DcaptureScreenshots=${params.CAPTURE_SCREENSHOTS} -DenableLogs=${params.ENABLE_LOGS} -DenableExtentReports=${params.ENABLE_EXTENT_REPORTS}"
                    bat mvnCommand
                }
            }
        }
        stage('Post-build Actions') {
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
