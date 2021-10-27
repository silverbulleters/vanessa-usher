/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration

@Field
PipelineConfiguration config

void call(PipelineConfiguration config) {
  boolean needPublish = common.needPublishTests(config)

  if (!needPublish) {
    return
  }

  this.config = config

  stage("Reports publish") {
    publish()
  }

}

private def publish() {
  def reports = []

  if (config.getStages().isSyntaxCheck()) {
    addToReport(reports, config.getSyntaxCheckOptional().getAllurePath())
    unpackResult(config.getSyntaxCheckOptional().stashes)
  }

  if (config.getStages().isSmoke()) {
    addToReport(reports, config.getSmokeOptional().getAllurePath())
    unpackResult(config.getSmokeOptional().stashes)
  }

  if (config.getStages().isTdd()) {
    addToReport(reports, config.getTddOptional().getAllurePath())
    unpackResult(config.getTddOptional().stashes)
  }

  if (config.getStages().isBdd()) {
    addToReport(reports, config.getBddOptional().getAllurePath())
    unpackResult(config.getBddOptional().stashes)
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
  stashes.every {entry ->
    dir(entry.value) {
      unstash entry.key
    }
  }
}

private unpackTestResults(String path, String id) {
  dir(path) {
    unstash "${id}-allure"
  }
  dir(config.getJunitPath()) {
    unstash "${id}-junit"
  }
}

private void cleanup() {
  dir('out') {
    deleteDir()
  }
}
