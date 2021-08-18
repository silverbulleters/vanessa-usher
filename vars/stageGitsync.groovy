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
      node(config.getAgent()) {
        checkout scm
        syncInternal()
      }
    }
  }
}

private def syncInternal() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      runSync(credential)
    }
  } else {
    runSync('')
  }
}

private def runSync(String credential) {
  def command = [
      "gitsync",
      "%credentialID%",
      "--v8version", config.getV8Version(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "all", stageOptional.getConfigPath()
  ].join(" ")
  command = command.replace("%credentialID%", credential)
  // TODO: ищем ошибку "КРИТИЧНАЯОШИБКА" в логах, если есть - фейлим сборку этой ошибкой
  cmdRun(command)
}