/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */


import groovy.transform.Field
import org.silverbulleters.usher.NotificationInfo
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.NotificationMode
import org.silverbulleters.usher.util.Common

@Field
PipelineConfiguration config

@Field
NotificationInfo notificationInfo = new NotificationInfo()

void call() {
  call('pipeline.json')
}

void call(String pathToConfig) {

  def libraryVersion = Common.getLibraryVersion()
  print("Версия Vanessa.Usher: ${libraryVersion}")

  catchError(buildResult: 'FAILURE') {
    start(pathToConfig);
  }

  notificationInfo.status = "${currentBuild.currentResult}"
  notificationInfo.showTestResults = common.needPublishTests(config)

  if (currentBuild.currentResult == 'SUCCESS') {
    sendSuccessNotification()
  } else if (currentBuild.currentResult == 'FAILURE') {
    sendErrorNotification()
  }
}

void start(String pathToConfig) {
  node {
    def scmVariables = checkout scm
    init(pathToConfig, scmVariables)

    // gitsync
    gitsync(config)

    // ci
    edtTransform(config)
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

void init(String pathToConfig, scmVariables) {
  stage('Initializing') {
    config = getPipelineConfiguration(pathToConfig)
    fillNotificationInfo(scmVariables)
  }
}

void sendSuccessNotification() {
  if (config.getNotification().getMode() == NotificationMode.SLACK) {

    slackHelper.sendNotification(notificationInfo)

  } else if (config.getNotification().getMode() == NotificationMode.EMAIL) {

    emailHelper.sendNotification(config.notification.email, notificationInfo)

  } else {
    // TODO: вывод в лог
  }
}

void sendErrorNotification() {

  if (config.notification.mode == NotificationMode.SLACK) {

  } else if (config.notification.mode == NotificationMode.EMAIL) {

    emailHelper.sendNotification(config.notification.email, notificationInfo)

  } else {
    // TODO: вывод в лог
  }

}

void fillNotificationInfo(scmVariables) {
  // для slack only
  notificationInfo.channelId = config.notification.slack.channelName
  notificationInfo.projectName = "${env.JOB_NAME}"
  notificationInfo.buildNumber = "${env.BUILD_NUMBER}"
  notificationInfo.buildUrl = "${env.BUILD_URL}"
  notificationInfo.branchName = "${scmVariables.GIT_BRANCH}"
  notificationInfo.commitId = "${scmVariables.GIT_COMMIT}"
  notificationInfo.commitMessage = ""
}