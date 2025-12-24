pipeline {
  agent any

  environment {
    IMAGE_NAME = "api-pasteleria"
    TAG = "qa"
    ENV_FILE = "env/qa.env"
  }

  stages {

    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build JAR') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh """
          docker build -t ${IMAGE_NAME}:${TAG} .
        """
      }
    }

    stage('Deploy') {
      steps {
        sh """
          cd /home/ycieza/infra/docker
          ENV_FILE=${ENV_FILE} TAG=${TAG} docker compose up -d
        """
      }
    }
  }
}
