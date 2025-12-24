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

    stage('Build Docker Image') {
      steps {
        sh '''
          docker build -t ${IMAGE_NAME}:${TAG} .
        '''
      }
    }

    stage('Deploy') {
      steps {
        sh '''
          cd /infra/docker
          ENV_FILE=env/qa.env TAG=qa docker-compose up -d --build
        '''
      }
    }

  }
}
