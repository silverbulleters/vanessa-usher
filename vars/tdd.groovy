/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.config.stage.TddOptional

@Field
PipelineConfiguration config

@Field
TddOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isTdd()) {
    return
  }

  this.config = config
  this.stageOptional = config.getTddOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения модульного тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
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
      def credentialTestClient = getTestClient()
      xddTesting(credential, credentialTestClient)
    }
  } else {
    xddTesting('')
  }
}

private xddTesting(String credential, String credentialTestClient) {
  command = vrunner.xunit(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  command = command.replace("%credentialTestClientID%", credentialTestClient)
  cmdRun(command)
}

// FIXME: ДУБЛЬ
private String getTestClient() {
  def baseValue = '%s:%s:1538'
  login = "${USERNAME}"
  pass = ""
  try {
    pass = "${PASSWORD}"
  } catch (e) {
  }
  def credentialTestClient = String.format('%s:%s:1538', login, pass)
  return credentialTestClient
}
