/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.PipelineConfiguration

@Field
PipelineConfiguration config

void call(PipelineConfiguration config) {
  boolean needPublish = config.getStages().isSyntaxCheck() || config.getStages().isSmoke() || config.getStages().isTdd() || config.getStages().isBdd()

  if (!needPublish) {
    return
  }

  this.config = config

  stage("Reports publish") {
    node {
      publish()
    }
  }

}

private def publish() {
  def reports = []

  if (config.getStages().isSyntaxCheck()) {
    addToReport(reports, config.getSyntaxCheckOptional().getAllurePath())
    unpackTestResults(config.getSyntaxCheckOptional().getAllurePath(), config.getSyntaxCheckOptional().getId())
  }

  if (config.getStages().isSmoke()) {
    addToReport(reports, config.getSmokeOptional().getAllurePath())
    unpackTestResults(config.getSmokeOptional().getAllurePath(), config.getSmokeOptional().getId())
  }

  if (config.getStages().isTdd()) {
    addToReport(reports, config.getTddOptional().getAllurePath())
    unpackTestResults(config.getTddOptional().getAllurePath(), config.getTddOptional().getId())
  }

  if (config.getStages().isBdd()) {
    addToReport(reports, config.getBddOptional().getAllurePath())
    unpackTestResults(config.getBddOptional().getAllurePath(), config.getBddOptional().getId())
  }

  junit allowEmptyResults: true, skipPublishingChecks: true, skipMarkingBuildUnstable: true, testResults: '**/out/junit/*.xml'
  allure includeProperties: false, jdk: '', results: reports
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

private unpackTestResults(String path, String id) {
  dir(path) {
    unstash "${id}-allure"
  }
  dir(config.getJunitPath()) {
    unstash "${id}-junit"
  }
}