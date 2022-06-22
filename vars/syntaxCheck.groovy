/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.state.PipelineState
import org.silverbulleters.usher.wrapper.VRunner

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
SyntaxCheckOptional stageOptional

@Field
Map result = [
    "junit" : [],
    "allure": [],
    "error": false
]

/**
 * Выполнить синтаксическую проверку конфигурации
 * @param config конфигурацию
 * @param state состояние конвейера
 */
void call(PipelineConfiguration config, SyntaxCheckOptional stageOptional, PipelineState state) {
  this.config = config
  this.state = state
  this.stageOptional = stageOptional

  infobaseHelper.unpackInfobase(config: config, state: state)

  catchError(message: 'Ошибка во время выполнения синтаксической проверки', buildResult: 'FAILURE', stageResult: 'FAILURE') {
    check()
  }

  testResultsHelper.archiveTestResults(
        name: stageOptional.name,
        junit: result.junit,
        allure: result.allure,
        stashes: state.syntaxCheck.stashes
    )

}

private def check() {
  def auth = config.defaultInfobase.auth
  if (credentialHelper.authIsPresent(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'DBUSERNAME', passwordVariable: 'DBPASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      syntaxCheck(credential)
    }
  } else {
    syntaxCheck()
  }
}

private def syntaxCheck(credential = '') {

  syntaxCheckByOptional(credential)
  if (stageOptional.checkExtensions) {
    syntaxCheckByOptional(credential, true)
  }

  if (result.error) {
    setFailureStatusStage("Во время выполнения syntax-check были ошибки")
  }

}

private def syntaxCheckByOptional(String credential, boolean checkExtensions = false) {
  def allureUuid = UUID.randomUUID().toString()
  def junitUuid = UUID.randomUUID().toString()

  def allurePath = "out/${allureUuid}"
  def junitPath = "out/${junitUuid}/${junitUuid}.xml"

  result.allure += allurePath
  result.junit += junitPath

  def command = VRunner.syntaxCheck(
      config: config,
      setting: stageOptional,
      existsExceptionFile: fileExists(stageOptional.exceptionFile),
      checkExtensions: checkExtensions,
      allurePath: allurePath,
      junitPath: junitPath
  )
  command = command.replace("%credentialID%", credential)

  try {
    cmdRun(command)
  } catch (exception) {
    result.error = true
    logger.info(exception.getMessage())
  }

}
