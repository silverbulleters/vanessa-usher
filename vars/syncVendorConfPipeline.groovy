/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.NotificationInfo
import org.silverbulleters.usher.config.PipelineConfiguration

import java.util.concurrent.TimeUnit

@Field
PipelineConfiguration config

@Field
NotificationInfo notificationInfo = new NotificationInfo()

@Field
String nodeForReadConfig

/**
 * Конвейер синхронизации релизов 1С с помощью yard
 * @param pathToConfig путь к конфигурационному файлу
 * @param nodeForRead имя или метка ноды для чтения конфигурации конвейера (необязательный)
 */
void call(String pathToConfig, String nodeForRead = '') {

  nodeForReadConfig = nodeForRead
  config = readPipelineConfig(pathToConfig, nodeForReadConfig)

  pipeline {

    agent { label config.agent }
    options { timestamps() }

    stages {

      stage('Prepare') {

        steps {

          script {

            notificationInfo.channelId = config.notification.slack.channelName
            notificationInfo.projectName = "${env.JOB_NAME}"
            notificationInfo.buildNumber = "${env.BUILD_NUMBER}"
            notificationInfo.buildUrl = "${env.BUILD_URL}"
            notificationInfo.branchName = "${env.GIT_BRANCH}"
            notificationInfo.commitId = "${env.GIT_COMMIT}"
            notificationInfo.commitMessage = ""

          }

        }

      }

      stage('Sync release 1s') {
        when { expression { config.stages.yard } }
        options {
          timeout(time: config.yardOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { yard(config, config.yardOptional) }
        }
      }

    }

    post {

      always {

        node(config.agent) {

          script {

            notificationInfo.status = "${currentBuild.currentResult}"
            postNotification(config, notificationInfo)

          }

        }

      }

    }
  }

}