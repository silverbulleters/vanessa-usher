import com.cloudbees.groovy.cps.NonCPS
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.additional.Repo
import org.silverbulleters.usher.config.ConfigurationReader

@Field
PipelineConfiguration config

void call() {
  call('pipeline.json')
}

void call(String pathToConnfig) {

  catchError(buildResult: 'FAILURE') {
    start(pathToConnfig);
  }

  if (currentBuild.result == 'FAILURE') {
    sendNotification()
  } else {
    // success
  }
}

void start(String pathToConnfig) {
  node {
    checkout scm
    init(pathToConnfig)

    // gitsync
    gitsync(config) // скрипт

    // ci
    prepareBase(config) // скрипт
    syntaxCheck(config) // скрипт
    sonarAnalyze(config) // скрипт
    smoke(config) // скрипт
    tdd(config) // скрипт
    bdd(config) // скрипт
    allurePublish(config) // скрипт
    build(config) // скрипт
  }
}

void init(String pathToConnfig) {
  stage('Initializing') {
    config = getPipelineConfiguration(pathToConnfig)
  }
}

def sendNotification() {
  emailext(
      body: "Подробности по ссылке ${env.BUILD_URL}.",
      subject: "Ошибка. Задача '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      to: config.getEmailForNotification()
  )
}