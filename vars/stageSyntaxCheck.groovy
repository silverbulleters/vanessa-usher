/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.state.PipelineState

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

def call(PipelineConfiguration config, PipelineState state) {
  if (!config.getStages().isSyntaxCheck()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSyntaxCheckOptional()
  this.state = state

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {

    stage(stageOptional.getName()) {

      infobaseHelper.unzipInfobase(config: config, state: state)

      catchError(message: 'Ошибка во время выполнения синтаксической проверки', buildResult: 'FAILURE',
          stageResult: 'FAILURE') {

        check()

      }

      catchError(message: 'Ошибка во время публикации отчетов о тестировании', buildResult: 'FAILURE',
          stageResult: 'FAILURE') {

        testResultsHelper.archiveTestResults(
            name: stageOptional.name,
            junit: result.junit,
            allure: result.allure,
            stashes: state.syntaxCheck.stashes
        )

      }

    }

  }
}

private def check() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
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
    failure("Во время выполнения syntax-check были ошибки")
  }

}

private def syntaxCheckByOptional(String credential, boolean checkExtensions = false) {
  def allureUuid = UUID.randomUUID().toString()
  def junitUuid = UUID.randomUUID().toString()

  def allurePath = "out/${allureUuid}"
  def junitPath = "out/${junitUuid}/${junitUuid}.xml"

  result.allure += allurePath
  result.junit += junitPath

  def command = vrunner.syntaxCheck(
      config: config,
      setting: stageOptional,
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