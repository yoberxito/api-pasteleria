pipeline {
  agent any

  environment {
    IMAGE_NAME = "api-pasteleria"
    TAG = "qa"
  }

  stages {

    stage('Build Docker Image') {
      steps {
        sh '''
          docker build -t ${IMAGE_NAME}:${TAG} .
        '''
      }
    }

    stage('Build Finished') {
      steps {
        echo "✅ Imagen ${IMAGE_NAME}:${TAG} construida correctamente"
      }
    }

  }
}
