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
    node() {
      checkout scm
      publish()
    }
  }

}

private def publish() {
  def reports = []

  if (config.getStages().isSyntaxCheck()) {
    addToReport(reports, config.getSyntaxCheckOptional().getAllurePath())
    unpackTestResults(config.getSyntaxCheckOptional().getAllurePath(), 'syntax-allure', 'syntax-junit')
  }

  if (config.getStages().isSmoke()) {
    addToReport(reports, config.getSmokeOptional().getAllurePath())
    unpackTestResults(config.getSmokeOptional().getAllurePath(), 'smoke-allure', 'smoke-junit')
  }

  if (config.getStages().isTdd()) {
    addToReport(reports, config.getTddOptional().getAllurePath())
    unpackTestResults(config.getTddOptional().getAllurePath(), 'tdd-allure', 'tdd-junit')
  }

  if (config.getStages().isBdd()) {
    addToReport(reports, config.getBddOptional().getAllurePath())
    unpackTestResults(config.getBddOptional().getAllurePath(), 'bdd-allure', 'bdd-junit')
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

private unpackTestResults(String path, String allure, String junit) {
  dir(path) {
    unstash allure
  }
  dir(UsherConstant.JUNIT_PATH) {
    unstash junit
  }
}