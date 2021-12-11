/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
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

void call(PipelineConfiguration config, PipelineState state) {
  boolean needPublish = common.needPublishTests(config)

  if (!needPublish) {
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

  if (config.getStages().isSyntaxCheck()) {
    state.syntaxCheck.stashes.each { key, value ->
      logger.info("Путь к отчету " + value)
      if (!key.startsWith("junit_")) {
        addToReport(reports, value)
      }
    }
    unpackResult(state.syntaxCheck.stashes)
  }

  if (config.getStages().isSmoke()) {
    addToReport(reports, config.getSmokeOptional().getAllurePath())
    unpackResult(state.smoke.stashes)
  }

  if (config.getStages().isTdd()) {
    addToReport(reports, config.getTddOptional().getAllurePath())
    unpackResult(state.tdd.stashes)
  }

  if (config.getStages().isBdd()) {
    addToReport(reports, config.getBddOptional().getAllurePath())
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
