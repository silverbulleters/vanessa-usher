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
      checkout scm

      syncInternal()
    }
  }
}

private def syncInternal() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getAuthString()

      def authStorage = stageOptional.auth
      if (credentialHelper.authIsPresent(authStorage) && credentialHelper.exist(authStorage)) {
        withCredentials([usernamePassword(credentialsId: authStorage, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          runSync(credential, credentialHelper.getAuthRepoString())
        }
        return
      }
      runSync(credential, '')
    }
  } else {
    def authStorage = stageOptional.auth
    if (credentialHelper.authIsPresent(authStorage) && credentialHelper.exist(authStorage)) {
      withCredentials([usernamePassword(credentialsId: authStorage, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        runSync('', credentialHelper.getAuthRepoString())
      }
      return
    }
    runSync('', '')
  }
}

private void runSync(String credential, String credentialStorage) {
  def command = [
      "gitsync",
      "%credentialID%",
      "--v8version", config.getV8Version(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "all",
      "%credentialStorageID%",
      stageOptional.getConfigPath()
  ].join(" ")
  command = command.replace("%credentialID%", credential)
  command = command.replace("%credentialStorageID%", credentialStorage)

  // TODO: ищем ошибку "КРИТИЧНАЯОШИБКА" в логах, если есть - фейлим сборку этой ошибкой
  cmdRun(command)
}