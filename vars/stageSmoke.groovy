/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.state.PipelineState

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
SmokeOptional stageOptional

void call(PipelineConfiguration config, PipelineState state) {
  if (!config.getStages().isSmoke()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSmokeOptional()
  this.state = state

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {

      if (config.stages.prepareBase && state.prepareBase.localBuildFolder) {
        print('Распаковка каталога "build"')
        unstash 'build-folder'
      }

      catchError(message: 'Ошибка во время выполнения дымового тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        testing()
      }

      if (fileExists(stageOptional.getAllurePath())) {
        allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
        testResultsHelper.archive(config, stageOptional, state.smoke)
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