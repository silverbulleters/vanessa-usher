import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration

@Field
PipelineConfiguration config

void call() {
  call('pipeline.json')
}

void call(String pathToConfig) {

  catchError(buildResult: 'FAILURE') {
    start(pathToConfig);
  }

  if (currentBuild.result == 'FAILURE') {
    sendNotification()
  } else {
    // success
  }
}

void start(String pathToConfig) {
  node {
    checkout scm
    init(pathToConfig)

    // gitsync
    gitsync(config)

    // ci
    prepareBase(config)
    syntaxCheck(config)
    smoke(config)
    tdd(config)
    bdd(config)
    sonarAnalyze(config)
    build(config)
    reportPublish(config)
  }
}

void init(String pathToConfig) {
  stage('Initializing') {
    config = getPipelineConfiguration(pathToConfig)
  }
}

def sendNotification() {
  emailext(
      body: "Подробности по ссылке ${env.BUILD_URL}.",
      subject: "Ошибка. Задача '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      to: config.getEmailForNotification()
  )
}