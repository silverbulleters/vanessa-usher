/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.BuildOptional
import org.silverbulleters.usher.state.PipelineState
import org.silverbulleters.usher.wrapper.Packman

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
BuildOptional stageOptional

/**
 * Собрать файлы поставки
 * @param config конфигурацию
 * @param state состояние конвейера
 */
void call(PipelineConfiguration config, BuildOptional stageOptional, PipelineState state) {
  this.config = config
  this.state = state
  this.stageOptional = stageOptional

  infobaseHelper.unpackInfobase(config: config, state: state)

  catchError(message: 'Ошибка во время сборки поставки', buildResult: 'FAILURE', stageResult: 'FAILURE') {

    if (stageOptional.errorIfJobStatusOfFailure && currentBuild.currentResult == 'FAILURE') {
      def message = "Сборка поставки отменена. Статус выполнения предыдущих этапов $currentBuild.currentResult"
      throw new Exception(message)
    }

    runBuild()
    archiving()

  }

}

private void runBuild() {
  def auth = config.defaultInfobase.auth
  if (credentialHelper.authIsPresent(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getCustomAuth("-db-user", "-db-pwd")
      runBuildInternal(credential)
    }
  } else {
    runBuildInternal()
  }
}

private void runBuildInternal(String credential = '') {
  def command = Packman.setDatabase(config, credential)
  cmdRun(command)

  command = Packman.makeCf(config)
  cmdRun(command)
}

private void archiving() {
  if (fileExists(stageOptional.getDistPath())) {
    archiveArtifacts stageOptional.getDistPath()
  }
}