pipeline {
    agent any

    tools {
        maven 'Maven-3.9.5'
    }

    environment {
        TOMCAT_HOME = '/home/livcy/dev/apache-tomcat-10.0.27'
        WAR_NAME = 'quest-lima-1.0-SNAPSHOT.war'
    }

    stages {
        stage('Source') {
            steps {
                sh 'mvn --version'
                sh 'git --version'
                git branch: 'JenkinsTest',
                    url: 'https://github.com/parfenovds/QuestLima/'
            }
        }

        stage('Build and Test') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn clean package sonar:sonar"
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(execPattern: '**/target/*.exec')
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                script {
                    sh "cp target/${WAR_NAME} ${TOMCAT_HOME}/webapps"
                }
            }
        }
    }
}
