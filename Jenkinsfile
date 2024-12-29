```groovy
pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'your-dockerhub-username/your-repo-name'
        DOCKER_CREDENTIALS = 'dockerhub-credentials'
    }
    
    stages {
        stage('Clone Repo') {
            steps {
                git branch: 'main', url: 'https://github.com/yourusername/your-repo.git'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${env.BUILD_NUMBER}")
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS) {
                        docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}").push()
                        docker.image("${DOCKER_IMAGE}:${env.BUILD_NUMBER}").push('latest')
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo 'Build and Docker Push Success!'
        }
        failure {
            echo 'Build Failed!'
        }
    }
}
```
