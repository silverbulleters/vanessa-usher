/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.YardOptional

@Field
PipelineConfiguration config

@Field
YardOptional stageOptional

/**
 * Запустить yard для работы с релизами 1С
 * @param config
 */
void call(PipelineConfiguration config, YardOptional stageOptional) {
  this.config = config
  this.stageOptional = stageOptional

  def auth = stageOptional.auth
  if (credentialHelper.authIsPresent(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      withEnv(["YARD_RELEASES_USER=${USERNAME}", "YARD_RELEASES_PWD=${PASSWORD}"]) {
        yardInternal()
      }
    }
  } else {
    yardInternal()
  }

}

private void yardInternal() {
  prepare()
  runYard()
  gitPush()
}

private void prepare() {
  def separator = getSeparator()
  def copyName = getCopyName()

  def descriptionPath = "${stageOptional.descriptionPath}"
  if (descriptionPath.startsWith("./") || descriptionPath.startsWith(".\\")) {
    descriptionPath = descriptionPath.substring(2)
  }

  dir("distr") {
    deleteDir()
  }

  cmdRun("mkdir distr")

  dir("distr") {
    cmdRun("mkdir ${stageOptional.appName}")
  }

  cmdRun("${copyName} ${descriptionPath} distr${separator}description.json")
  cmdRun("${copyName} ${descriptionPath} distr${separator}${stageOptional.appName}${separator}description.json")

  dir("tmplts") {
    deleteDir()
  }

}

private void runYard() {
  def debugFlag = stageOptional.debug ? "-v" : ""

  def command = [
      'yard',
      debugFlag,
      'process',
      '--work-dir', stageOptional.workspacePath,
      stageOptional.yardSettingsPath
  ].join(" ")

  cmdRun(command)
}

private void gitPush() {
  // TODO: вынести источник origin в настройки
  def command = "git push --set-upstream origin ${stageOptional.branch}"
  cmdRun(command)
}

private String getSeparator() {
  def separator = '\\'
  if (isUnix()) {
    separator = '/'
  }
  return separator
}

private String getCopyName() {
  def copyName = 'copy'
  if (isUnix()) {
    copyName = 'cp'
  }
  return copyName
}
