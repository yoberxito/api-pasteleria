pipeline {
    agent any

    triggers {
        githubPush()
    }

    environment {
        IMAGE_NAME = "api-pasteleria"
        TAG = "qa"
        DOCKER_COMPOSE_FILE = "/infra/docker/docker-compose.yml"
    }

    stages {

        stage('Build Docker Image') {
            steps {
                echo "🐳 Construyendo imagen Docker ${IMAGE_NAME}:${TAG}"
                sh "docker build -t ${IMAGE_NAME}:${TAG} ."
            }
        }
        stage('Deploy with Docker Compose') {
          steps {
            sh """
              cd /infra/docker
              docker compose up -d --build api-pasteleria
            """
          }
        }



        stage('Finished') {
            steps {
                echo "✅ Deploy exitoso"
            }
        }
    }


    post {
        failure {
            echo "❌ El despliegue falló"
        }
    }
}

