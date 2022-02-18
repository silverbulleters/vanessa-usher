pipeline {
  agent {
    label "docker && linux"
  }


  stages {
    stage("Build and Check") {
      steps {
        script {
          withDockerContainer(image: 'openjdk:8-jdk',
              args: '-v "$HOME/jdk11/.m2":/root/.m2 -e JAVA_OPTIONS="-Xmx4G -Dorg.gradle.jvmargs=-Xmx4096m"') {
            sh "./gradlew check"
          }
        }
      }
    }
  }
}