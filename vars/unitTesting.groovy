/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.TddOptional
import org.silverbulleters.usher.state.PipelineState
import org.silverbulleters.usher.wrapper.VRunner

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
TddOptional stageOptional

/**
 * Выполнить модульное тестирование
 * @param config конфигурация
 * @param state состояние конвейера
 */
void call(PipelineConfiguration config, TddOptional stageOptional, PipelineState state) {
  this.config = config
  this.state = state
  this.stageOptional = stageOptional

  infobaseHelper.unpackInfobase(config: config, state: state)

  catchError(message: 'Ошибка во время выполнения дымового тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
    testing()
  }

  catchError(message: 'Ошибка во время архивации отчетов о тестировании', buildResult: 'FAILURE', stageResult: 'FAILURE') {
    testResultsHelper.packTestResults(config, stageOptional, state.tdd)
  }

}

private def testing() {
  def auth = config.defaultInfobase.auth
  if (credentialHelper.authIsPresent(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'DBUSERNAME', passwordVariable: 'DBPASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      def credentialTestClient = credentialHelper.getTestClientWithAuth()
      xddTesting(credential, credentialTestClient)
    }
  } else {
    xddTesting()
  }
}

private xddTesting(String credential = '', String credentialTestClient = '') {
  def testClient = credentialTestClient.isEmpty() ? "::1538" : credentialTestClient

  def command = VRunner.xunit(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  command = command.replace("%credentialTestClientID%", testClient)
  cmdRun(command)
}