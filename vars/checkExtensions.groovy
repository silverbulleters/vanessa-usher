/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.CheckExtensionsOptional
import org.silverbulleters.usher.state.PipelineState
import org.silverbulleters.usher.wrapper.VRunner


@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
CheckExtensionsOptional stageOptional

@Field
Map result = [
        "junit" : [],
        "allure": [],
        "error": false
]

/**
 * Выполнить проверку применимости расширения для использования в конкретной информационной базе
 * @param config конфигурацию
 * @param stageOptional дополнительные параметры
 * @param state состояние конвейера
 */
void call(PipelineConfiguration config, CheckExtensionsOptional stageOptional, PipelineState state) {
  this.config = config
  this.state = state
  this.stageOptional = stageOptional

  catchError(message: 'Ошибка во время выполнения проверки применимости расширений', buildResult: 'FAILURE', stageResult: 'FAILURE') {
    check()
  }

  testResultsHelper.archiveTestResults(
          name: stageOptional.name,
          junit: result.junit,
          allure: result.allure,
          stashes: state.checkExtensions.stashes
  )

}

private def check() {
  def auth = config.defaultInfobase.auth
  if (credentialHelper.authIsPresent(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'DBUSERNAME', passwordVariable: 'DBPASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      CheckExtension(credential)
    }
  } else {
    CheckExtension()
  }
}

private def CheckExtension(credential = '') {

  CheckExtensionByOptional(credential)

}

private def CheckExtensionByOptional(String credential) {

  def command = VRunner.checkCanApplyExtensions(
          config,
          stageOptional
  )

  command = command.replace("%credentialID%", credential)

  auth = stageOptional.getRepo().getAuth()

  withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
    def credentialRepo = credentialHelper.getAuthRepoString()
    command = command.replace("%credentialStorageID%", credentialRepo)
    try {
      cmdRun(command)
    } catch (exception) {
      result.error = true
      logger.info(exception.getMessage())
    }
  }
}
