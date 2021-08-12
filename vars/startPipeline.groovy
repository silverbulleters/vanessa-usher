/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.util.Common

@Field
PipelineConfiguration config

void call() {
  call('pipeline.json')
}

void call(String pathToConfig) {

  def libraryVersion = Common.getLibraryVersion()
  print("Версия Vanessa.Usher: ${libraryVersion}")

  catchError(buildResult: 'FAILURE') {
    start(pathToConfig);
  }

  if (currentBuild.result == 'FAILURE') {
    sendEmailNotification()
  } else {
    // success
  }
}

void start(String pathToConfig) {
  node {
    checkout scm
    init(pathToConfig)

    // gitsync
    stageGitsync(config)

    // ci
    stageEdtTransform(config)
    stagePrepareBase(config)
    stageSyntaxCheck(config)
    stageSmoke(config)
    stageTdd(config)
    stageBdd(config)
    stageSonarAnalyze(config)
    stageBuild(config)
    stageReportPublish(config)
  }
}

void init(String pathToConfig) {
  stage('Initializing') {
    config = getPipelineConfiguration(pathToConfig)
  }
}

def sendEmailNotification() {
  if (config.getEmailForNotification() == UsherConstant.EMPTY_VALUE) {
    return
  }
  emailext(
      body: "Подробности по ссылке ${env.BUILD_URL}.",
      subject: "Ошибка. Задача '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      to: config.getEmailForNotification()
  )
}