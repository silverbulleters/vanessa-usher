/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import hudson.tasks.test.AbstractTestResultAction
import org.silverbulleters.usher.NotificationInfo
import org.silverbulleters.usher.config.PipelineConfiguration

/**
 * Создать и заполнить информацию для уведомления
 * @param config конфигурация
 * @return информация для уведомления
 */
NotificationInfo call(PipelineConfiguration config) {
  def notificationInfo = new NotificationInfo()

  notificationInfo.channelId = config.notification.slack.channelName
  notificationInfo.projectName = "${env.JOB_NAME}"
  notificationInfo.buildNumber = "${env.BUILD_NUMBER}"
  notificationInfo.buildUrl = "${env.BUILD_URL}"
  notificationInfo.branchName = "${env.GIT_BRANCH}"
  notificationInfo.commitId = "${env.GIT_COMMIT}"
  notificationInfo.commitMessage = ""

  notificationInfo.status = "${currentBuild.currentResult}"
  notificationInfo.showTestResults = common.needPublishTests(config)
  if (notificationInfo.showTestResults) {
    fillSummary(notificationInfo)
  }

  return notificationInfo
}

private void fillSummary(def info) {
  def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
  if (testResultAction == null) {
    info.showTestResults = false
    return
  }

  info.failedCount = testResultAction.getFailCount()
  info.skippedCount = testResultAction.getSkipCount()
  info.successCount = testResultAction.getTotalCount() - info.failedCount - info.skippedCount
}