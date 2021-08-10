/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.config.stage.SmokeOptional

@Field
PipelineConfiguration config

@Field
BddOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isBdd()) {
    return
  }

  this.config = config
  this.stageOptional = config.getBddOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения bdd тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          testing()
        }
        if (fileExists(stageOptional.getAllurePath())) {
          allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
        }
      }
    }
  }
}

private def testing() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentialHelper.exist(auth)) {
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