/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.GitsyncOptional

@Field
PipelineConfiguration config

@Field
GitsyncOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isGitsync()) {
    return
  }

  this.config = config
  this.stageOptional = config.getGitsyncOptional()

  timeout(unit: 'MINUTES', time: config.getTimeout()) {

    stage(stageOptional.getName()) {

      catchError(message: 'Ошибка во время выполнения gitsync', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        syncInternal()
      }

    }

  }
}

private void syncInternal() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      syncInternalWithRepoAuth(credentialHelper.getAuthString())
      return
    }
  }

  syncInternalWithRepoAuth('')
}

private void syncInternalWithRepoAuth(String credential) {
  def authStorage = stageOptional.auth
  if (credentialHelper.authIsPresent(authStorage) && credentialHelper.exist(authStorage)) {
    withCredentials([usernamePassword(credentialsId: authStorage, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      runSync(credential, credentialHelper.getAuthRepoString())
    }
    return
  }
  runSync(credential, '')
}

private void runSync(String credential, String credentialStorage) {

  def commandPathOne = [
      "gitsync",
      "%credentialID%",
      "--v8version", config.getV8Version()
  ]

  if (!stageOptional.useTemporaryInfobase) {
    commandPathOne += "--ibconnection ${config.defaultInfobase.connectionString}"
  }

  if (!stageOptional.tempPath.isEmpty()) {
    commandPathOne += "--tempdir \"${stageOptional.tempPath}\""
  }

  commandPathOne += [
      "all",
      "%credentialStorageID%",
      stageOptional.getConfigPath()
  ]

  def command = commandPathOne.join(" ")

  command = command.replace("%credentialID%", credential)
  command = command.replace("%credentialStorageID%", credentialStorage)

  // TODO: ищем ошибку "КРИТИЧНАЯОШИБКА" в логах, если есть - фейлим сборку этой ошибкой
  cmdRun(command)

}
