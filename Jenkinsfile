pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'latu03/userservice'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test and Package') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package'
            }
        }

        stage('Verify JAR') {
            steps {
                sh 'ls -lh target/UserService-0.0.1-SNAPSHOT.jar'
                archiveArtifacts(
                    artifacts: 'target/UserService-0.0.1-SNAPSHOT.jar',
                    fingerprint: true
                )
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t "$DOCKER_IMAGE:$BUILD_NUMBER" .'
                sh 'docker tag "$DOCKER_IMAGE:$BUILD_NUMBER" "$DOCKER_IMAGE:latest"'
            }
        }

        stage('Push to Docker Hub') {
            environment {
                DOCKER_CREDENTIALS = credentials('dockerhub-credentials')
            }

            steps {
                sh '''
                    set +x
                    echo "$DOCKER_CREDENTIALS_PSW" |
                      docker login \
                        --username "$DOCKER_CREDENTIALS_USR" \
                        --password-stdin

                    docker push "$DOCKER_IMAGE:$BUILD_NUMBER"
                    docker push "$DOCKER_IMAGE:latest"
                '''
            }
        }
    }

    post {
        success {
            echo "CI succeeded: $DOCKER_IMAGE:$BUILD_NUMBER"
        }

        failure {
            echo 'CI failed. Open the failed stage and inspect its console output.'
        }

        always {
            sh 'docker logout || true'
        }
    }
}