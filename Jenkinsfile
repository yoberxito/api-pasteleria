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

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Construyendo imagen Docker ${IMAGE_NAME}:${TAG}"
                sh "docker build -t ${IMAGE_NAME}:${TAG} ."
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo "🚀 Desplegando API con docker-compose"
                sh "/usr/local/bin/docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build api-pasteleria"
            }
        }

        stage('Finished') {
            steps {
                echo "✅ API actualizada y desplegada correctamente"
                echo "🌐 https://api-pasteleria.yccweb.uk"
            }
        }
    }

    post {
        failure {
            echo "❌ El despliegue falló. Revisa los logs del pipeline."
        }
        success {
            echo "🎉 Deploy exitoso"
        }
    }
}
