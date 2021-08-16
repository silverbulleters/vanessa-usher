/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */

import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SmokeOptional

@Field
PipelineConfiguration config

@Field
SmokeOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isSmoke()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSmokeOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения дымового тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          testing()
        }
        if (fileExists(stageOptional.getAllurePath())) {
          allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
          testResultsHelper.archive(config, stageOptional)
        }
      }
    }
  }
}

private def testing() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      def credentialTestClient = credentialHelper.getTestClientWithAuth()
      smokeTesting(credential, credentialTestClient)
    }
  } else {
    smokeTesting('', '')
  }
}

private smokeTesting(String credential, String credentialTestClient) {
  def testClient = credentialTestClient.isEmpty() ? "::1538" : credentialTestClient

  command = vrunner.smoke(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  command = command.replace("%credentialTestClientID%", testClient)
  cmdRun(command)
}