/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.state.PipelineState

@Field
PipelineConfiguration config

@Field
PipelineState state

/**
 * Опубликовать отчеты о тестировании
 * @param config конфигурация
 * @param state состояние конвейера
 */
void call(PipelineConfiguration config, PipelineState state) {
  if (!common.needPublishTests(config)) {
    return
  }

  this.config = config
  this.state = state

  stage("Reports publish") {
    publish()
  }

}

private def publish() {
  def reports = []

  if (config.stages.syntaxCheck) {
    state.syntaxCheck.stashes.each { key, value ->
      logger.info("Путь к отчету " + value)
      if (!key.startsWith("junit_")) {
        addToReport(reports, value)
      }
    }
    unpackResult(state.syntaxCheck.stashes)
  }

  if (config.stages.smoke) {
    addToReport(reports, config.smokeOptional.allurePath)
    unpackResult(state.smoke.stashes)
  }

  if (config.stages.tdd) {
    addToReport(reports, config.tddOptional.allurePath)
    unpackResult(state.tdd.stashes)
  }

  if (config.stages.bdd) {
    addToReport(reports, config.bddOptional.allurePath)
    unpackResult(state.bdd.stashes)
  }

  junit allowEmptyResults: true, skipPublishingChecks: true, skipMarkingBuildUnstable: true, testResults: '**/out/junit/*.xml'
  allure includeProperties: false, jdk: '', results: reports

  cleanup()
}

private addToReport(reports, String allurePath) {
  def path = getPrettyPath(allurePath)
  reports.add([path: path])
}

private String getPrettyPath(String path) {
  if (path.startsWith("./")) {
    return path.substring(2)
  }
  return path
}

private unpackResult(Map stashes) {
  stashes.each { key, value ->
    dir(value) {
      unstash key
    }
  }
}

private void cleanup() {
  dir('out') {
    deleteDir()
  }
}