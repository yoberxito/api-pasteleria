pipeline {
    agent any

    environment {
        IMAGE_NAME = "api-pasteleria"
        TAG = "qa"
        // Ruta dentro del contenedor Jenkins donde mapeamos tu infra
        DOCKER_COMPOSE_PATH = "/infra/docker/docker-compose.yml"
    }

    stages {
        stage('Build Docker Image') {
            steps {
                // Construye la nueva imagen con los cambios de GitHub
                sh "docker build -t ${IMAGE_NAME}:${TAG} ."
            }
        }

        stage('Deploy to Docker Compose') {
            steps {
                // Llamamos directamente al plugin de Docker para evitar confusiones de flags
                sh 'docker-compose -f /infra/docker/docker-compose.yml up -d --build api-pasteleria'
            }
        }
        stage('Build Finished') {
            steps {
                echo "✅ API actualizada y desplegada correctamente en https://api-pasteleria.yccweb.uk"
            }
        }
    }
}