/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.NotificationInfo
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.state.PipelineState

import java.util.concurrent.TimeUnit

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
String nodeForReadConfig

/**
 * Конвейер сборочной линии 1С
 * @param pathToConfig путь к конфигурационному файлу
 * @param nodeForRead имя или метка ноды для чтения конфигурации конвейера (необязательный)
 */
void call(String pathToConfig, String nodeForRead = '') {

  nodeForReadConfig = nodeForRead
  state = createPipelineState()
  config = readPipelineConfig(pathToConfig, nodeForReadConfig)

  pipeline {

    agent { label config.agent }
    options {
      timestamps()
    }

    stages {

      stage('EDT transform to XML') {
        when { expression { config.stages.edtTransform } }
        options {
          timeout(time: config.edtTransformOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { edtTransform(config, config.edtTransformOptional) }
        }
      }

      stage('Prepare infobase') {
        when { expression { config.stages.prepareBase } }
        options {
          timeout(time: config.prepareBaseOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { prepareInfobase(config, config.prepareBaseOptional, state) }
        }
      }

      stage('Run external') {
        when { expression { config.stages.runExternal } }
        options {
          timeout(time: config.runExternal.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { runExternalDataProcessors(config, config.runExternal, state) }
        }
      }

      stage('Check Extensions') {
        when { expression { config.stages.checkExtensions } }
        options {
          timeout(time: config.checkExtensionsOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { checkExtensions(config, config.checkExtensionsOptional, state) }
        }
      }

      stage('Syntax check') {
        when { expression { config.stages.syntaxCheck } }
        options {
          timeout(time: config.syntaxCheckOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { syntaxCheck(config, config.syntaxCheckOptional, state) }
        }
      }

      stage('Sonar analyze') {
        agent {
          label config.sonarQubeOptional.agent
        }
        when {
          beforeAgent true
          expression { config.stages.sonarqube }
        }
        options {
          timeout(time: config.sonarQubeOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { sonarAnalyze(config, config.sonarQubeOptional) }
        }
      }

      stage('Smoke testing') {
        when { expression { config.stages.smoke } }
        options {
          timeout(time: config.smokeOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { smokeTesting(config, config.smokeOptional, state) }
        }
      }

      stage('TDD') {
        when { expression { config.stages.tdd } }
        options {
          timeout(time: config.tddOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { unitTesting(config, config.tddOptional, state) }
        }
      }

      stage('BDD') {
        when { expression { config.stages.bdd } }
        options {
          timeout(time: config.bddOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { bddTesting(config, config.bddOptional, state) }
        }
      }

      stage('Distribution build') {
        when { expression { config.stages.build } }
        options {
          timeout(time: config.buildOptional.timeout, unit: TimeUnit.MINUTES)
        }

        steps {
          script { distributionBuild(config, config.buildOptional, state) }
        }
      }

    }

    post {

      always {

        node(config.agent) {
          script {
            publishReports(config, state)
          }

          script {
            def notificationInfo = createNotificationInfo(config)
            postNotification(config, notificationInfo)
          }

        }

      }

    }

  }

}
