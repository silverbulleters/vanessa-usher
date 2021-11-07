/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.state.PipelineState

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
BddOptional stageOptional

void call(PipelineConfiguration config, PipelineState state) {
  if (!config.getStages().isBdd()) {
    return
  }

  this.config = config
  this.stageOptional = config.getBddOptional()
  this.state = state

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      if (config.stages.prepareBase && state.prepareBase.localBuildFolder) {
        print('Распаковка каталога "build/ib"')
        unstash 'build-ib-folder'
      }

      catchError(message: 'Ошибка во время выполнения bdd тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        testing()
      }
      if (fileExists(stageOptional.getAllurePath())) {
        allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
        testResultsHelper.archive(config, stageOptional, state.bdd)
      }
    }
  }
}

private def testing() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      bddTesting(credential)
    }
  } else {
    bddTesting('')
  }
}

private bddTesting(String credential) {
  def command = vrunner.vanessa(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}