pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
    }

    parameters {
        string(name: 'URL', defaultValue: 'http://puppies.herokuapp.com/', description:'Enter the URL of the environment where the tests will be executed')
        choice(name: 'BROWSER', choices: ['Chrome', 'Firefox'], description: 'Select the browser to be used for the tests')
        string(name: 'TIMEOUT', defaultValue: '5', description:'Enter the time (in seconds) to wait before throwing an exception if the elment cannot be located')
        string(name: 'TAGS', defaultValue: '@smoke', description:'Enter the tags of the tests to be run. Enter @all to run the full suite. Supports Cucumber tags expressions like "@[tag-name] and not @[tag-name]"')
    }

    stages {
        stage('Build') {
            steps {
                bat "mvn -B -DskipTests clean package"
            }
        }

        stage('Test') {
            steps {
                bat "mvn test -Dcucumber.filter.tags=${params.TAGS} -Dbrowser=${params.BROWSER} -Durl=${params.URL} -Dtimeout=${params.TIMEOUT}"
            }
            post {
                always {
                    cucumber buildStatus: 'UNSTABLE',
                            fileIncludePattern: '**/*.json'
                    /*classifications: [
                    [key: 'Browser', value: '${params.BROWSER}']
                    ]*/
                }
            }
        }

        stage ('Deploy') {
            steps {
                echo "Deploying..."
            }
        }
    }

}