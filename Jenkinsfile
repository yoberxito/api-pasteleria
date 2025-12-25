pipeline {
  agent any

  environment {
    IMAGE_NAME = "api-pasteleria"
    TAG = "qa"
    COMPOSE_DIR = "/infra/docker"
    SERVICE_NAME = "api-pasteleria"
  }

  stages {

    stage('Build Docker Image') {
      steps {
        sh '''
          docker build -t ${IMAGE_NAME}:${TAG} .
        '''
      }
    }

    stage('Deploy API') {
      steps {
        sh '''
          cd ${COMPOSE_DIR}
          docker-compose up -d --no-deps ${SERVICE_NAME}
        '''
      }
    }

  }

  post {
    success {
      echo "✅ API desplegada correctamente"
    }
    failure {
      echo "❌ Error en el despliegue"
    }
  }
}
