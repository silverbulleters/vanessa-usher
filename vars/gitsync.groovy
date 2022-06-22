/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.GitsyncOptional
import org.silverbulleters.usher.wrapper.Gitsync

@Field
PipelineConfiguration config

@Field
GitsyncOptional stageOptional

/**
 * Запустить синхронизацию хранилища 1С и проекта git
 * @param config конфигурацию
 */
void call(PipelineConfiguration config, GitsyncOptional stageOptional) {
  this.config = config
  this.stageOptional = stageOptional

  syncInternal()
}

private void syncInternal() {
  def auth = config.defaultInfobase.auth
  if (credentialHelper.authIsPresent(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'DBUSERNAME', passwordVariable: 'DBPASSWORD')]) {
      syncInternalWithRepoAuth(credentialHelper.getAuthString())
    }
    return
  }

  syncInternalWithRepoAuth()
}

private void syncInternalWithRepoAuth(String credential = '') {
  def authStorage = stageOptional.auth
  if (credentialHelper.authIsPresent(authStorage)) {
    withCredentials([usernamePassword(credentialsId: authStorage, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      runSync(credential, credentialHelper.getAuthRepoString())
    }
    return
  }
  runSync(credential)
}

private void runSync(String credential = '', String credentialStorage = '') {
  def command = Gitsync.syncAll(config, stageOptional, credential, credentialStorage)
  // TODO: ищем ошибку "КРИТИЧНАЯОШИБКА" в логах, если есть - фейлим сборку этой ошибкой
  cmdRun(command)
}
