/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import com.cloudbees.groovy.cps.NonCPS
import groovy.transform.Field
import hudson.tasks.test.AbstractTestResultAction
import org.silverbulleters.usher.NotificationInfo
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.NotificationMode
import org.silverbulleters.usher.state.PipelineState
import org.silverbulleters.usher.util.Common

/**
 * Конфигурация
 */
@Field
PipelineConfiguration config

/**
 * Метка или имя узла для чтения конфигурационного файла
 */
@Field
String nodeForReadConfig

/**
 * Состояние
 */
@Field
PipelineState state

/**
 * Информация для уведомления
 */
@Field
NotificationInfo notificationInfo = new NotificationInfo()

void call() {

  logger.debug("Путь к конфигурационному файлу по умолчанию pipeline.json")

  call('pipeline.json')

}

/**
 * Запустить конвейер 1С
 * @param pathToConfig путь к конфигурационному файлу
 * @param nodeForReadConfig (опционально) имя узла или метки для чтения конфигурационного файла
 */
void call(String pathToConfig, String nodeForReadConfig = '') {

  logger.debug("Путь к конфигурационному файлу '${pathToConfig}'")

  this.nodeForReadConfig = nodeForReadConfig
  this.state = newPipelineState()

  def libraryVersion = Common.getLibraryVersion()
  logger.info("Версия Vanessa.Usher `${libraryVersion}`")

  readConfig(pathToConfig)

  catchError(buildResult: 'FAILURE') {
    start()
  }

  notificationInfo.status = "${currentBuild.currentResult}"
  notificationInfo.showTestResults = common.needPublishTests(config)
  if (notificationInfo.showTestResults) {
    fillSummaryTestResults()
  }

  if (currentBuild.currentResult == 'SUCCESS') {
    if (!config.stages.gitsync) {
      sendNotification()
    }
  } else if (currentBuild.currentResult == 'FAILURE') {
    sendNotification()
  }
}

private void readConfig(String pathToConfig) {
  logger.info("Чтение конфигурационного файла")

  def absolutePathToConfig = common.getAbsolutePathToConfig(pathToConfig)
  def file = new File(absolutePathToConfig)
  if (file.exists()) {
    logger.debug("Конфигурационный файл найден в workspace")
    config = getPipelineConfiguration(absolutePathToConfig)
  } else {

    logger.debug("Чтение конфигурационного файла на узле с checkout scm")

    readConfigInternal(nodeForReadConfig) {
      checkout scm
      config = getPipelineConfiguration(pathToConfig, true)
    }

  }
}

private void readConfigInternal(String label, Closure body) {

  if (label.isEmpty()) {
    node {
      body()
    }
  } else {
    node(label) {
      body()
    }
  }

}

private void start() {

  if (config.stages.gitsync) {

    logger.info("Это конвейер с gitsync")
    startGitSync()

  } else if (config.stages.yard) {
    logger.info("Это конвейер с yard")
    startYard()
  } else {

    logger.info("Это конвейер сборочной линии")
    startBuildPipeline()

  }

}

private void startGitSync() {

  node(config.getAgent()) {

    def scmVariables = checkout scm
    logger.debug("Чтение данных scm для уведомлений")
    fillNotificationInfo(scmVariables)

    stageGitsync(config)

  }

}

private void startYard() {

  node(config.getAgent()) {

    def scmVariables = checkout scm
    logger.debug("Чтение данных scm для уведомлений")
    fillNotificationInfo(scmVariables)

    stageYard(config)

  }

}

private void startBuildPipeline() {

  node(config.getAgent()) {
    def scmVariables = checkout scm

    logger.debug("Чтение данных scm для уведомлений")
    fillNotificationInfo(scmVariables)

    stageEdtTransform(config)
    stagePrepareBase(config, state)
    stageSyntaxCheck(config, state)

    testing()

    stageSonarAnalyze(config)

    stageBuild(config, state)

    stageReportPublish(config, state)
  }

}

private void testing() {

  if (config.matrixTesting.agents.size() == 0) {
    performTesting()
  } else {
    logger.info("Используется матричное тестирование")
    def jobs = [:]
    def count = 1
    config.matrixTesting.agents.each { agentName ->
      def name = "${count}. ${agentName}"
      jobs[name] = {
        node(agentName) {
          checkout scm
          performTesting()
        }
      }
      count++
    }

    parallel jobs
  }

}

private void performTesting() {
  stageSmoke(config, state)
  stageTdd(config, state)
  stageBdd(config, state)
}

private void sendNotification() {
  address = ""
  def providerNotification
  if (config.getNotification().getMode() == NotificationMode.SLACK) {
    providerNotification = slackHelper
    address = config.notification.slack.channelName
  } else if (config.getNotification().getMode() == NotificationMode.EMAIL) {
    providerNotification = emailHelper
    address = config.notification.email
  } else {
    return
  }

  if (config.stages.gitsync) {
    providerNotification.sendErrorNotification(address, notificationInfo)
  } else {
    providerNotification.sendNotification(address, notificationInfo)
  }
}

private void fillNotificationInfo(scmVariables) {
  // для slack only
  notificationInfo.channelId = config.notification.slack.channelName
  notificationInfo.projectName = "${env.JOB_NAME}"
  notificationInfo.buildNumber = "${env.BUILD_NUMBER}"
  notificationInfo.buildUrl = "${env.BUILD_URL}"
  notificationInfo.branchName = "${scmVariables.GIT_BRANCH}"
  notificationInfo.commitId = "${scmVariables.GIT_COMMIT}"
  notificationInfo.commitMessage = ""
}

@NonCPS
private void fillSummaryTestResults() {
  def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
  if (testResultAction == null) {
    notificationInfo.showTestResults = false
    return
  }
  notificationInfo.failedCount = testResultAction.getFailCount()
  notificationInfo.skippedCount = testResultAction.getSkipCount()
  notificationInfo.successCount = testResultAction.getTotalCount() - notificationInfo.failedCount - notificationInfo.skippedCount
}