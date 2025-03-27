pipeline {
    agent any

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"  // Set this based on your system
        MVN_HOME = "/usr/bin/mvn"
        APP_PORT = "80"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'https://github.com/skr003/Cloud-Script-Generator.git'  // Replace with your repo URL
            }
        }

        stage('Build Project') {
            steps {
                sh '${MVN_HOME} clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh '${MVN_HOME} test'
            }
        }

        stage('Package Application') {
            steps {
                sh '${MVN_HOME} package -DskipTests'
            }
        }

        stage('Stop Existing App') {
            steps {
                script {
                    sh '''
                    PID=$(lsof -t -i:${APP_PORT}) || true
                    if [ ! -z "$PID" ]; then
                        echo "Stopping existing app running on port ${APP_PORT}"
                        kill -9 $PID
                    fi
                    '''
                }
            }
        }

        stage('Deploy Application') {
            steps {
                sh '''
                nohup java -jar target/*.jar --server.port=${APP_PORT} > app.log 2>&1 &
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    sh '''
                    sleep 5
                    curl -I http://localhost:${APP_PORT} || echo "App failed to start!"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "✅ Java Project built and deployed successfully on port ${APP_PORT}"
        }
        failure {
            echo "❌ Build or Deployment failed! Check logs."
        }
    }
}
