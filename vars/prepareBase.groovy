/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.PrepareBaseOptional

@Field
PipelineConfiguration config

@Field
PrepareBaseOptional stageOptional

/**
 * Подготовка и обновление базы
 *
 * @param config
 */
void call(PipelineConfiguration config) {
  if (!config.getStages().isPrepareBase()) {
    return
  }

  this.config = config
  this.stageOptional = config.getPrepareBaseOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage('Prepare base') {
      node(config.getAgent()) {
        checkout scm
        prepare()
      }
    }
  }
}

private def prepare() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      prepareInternal(credential)
    }
  } else {
    prepareInternal('')
  }
}

private def prepareInternal(String credential) {
  if (stageOptional.getTemplate().isEmpty() || stageOptional.getTemplate() == UsherConstant.EMPTY_VALUE) {
    if (stageOptional.getRepo() != Repo.EMPTY) {
      loadRepo(credential)
      updateDB(credential)
    } else {
      initDevFromSource(credential)
    }
  } else {
    initDevWithTemplate(credential)
    compile(credential)
  }
  migrate(credential)
}

private def loadRepo(credential) {
  command = vrunner.loadRepo(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  auth = stageOptional.getRepo().getAuth()
  withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
    def credentialRepo = credentialHelper.getAuthRepoString()
    command = command.replace("%credentialStorageID%", credentialRepo)
    cmdRun(command)
  }
}

private def updateDB(credential) {
  command = vrunner.updateDB(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}

private def initDevWithTemplate(credential) {
  command = vrunner.initDevWithTemplate(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}

private def initDevFromSource(credential) {
  command = vrunner.initDevFromSource(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}

private def compile(credential) {
  command = vrunner.initDevWithTemplate(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}

private def migrate(credential) {
  command = vrunner.migrate(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}