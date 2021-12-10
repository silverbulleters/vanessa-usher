/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SonarQubeOptional

@Field
PipelineConfiguration config

@Field
SonarQubeOptional stageOptional

/**
 * Запустить анализ проекта в SonarQube
 * @param config конфигурацию
 */
void call(PipelineConfiguration config) {
  this.config = config
  this.stageOptional = config.sonarQubeOptional

  def projectVersion = getProjectVersion()
  def scannerHome = tool stageOptional.getToolId()

  def sonarDebugKey = ''
  if (stageOptional.isDebug()) {
    sonarDebugKey = '-X'
  }

  def sonarBranch = ''
  if (stageOptional.isUseBranch()) {
    def branch = "${env.GIT_BRANCH}"
    if (branch.startsWith('origin/')) {
      branch = branch.substring(7)
    }
    sonarBranch = "-Dsonar.branch.name=${branch}"
  }

  sonarCommand = "${scannerHome}/bin/sonar-scanner ${sonarDebugKey} ${projectVersion} ${sonarBranch}"
  withSonarQubeEnv("${stageOptional.getServerId()}") {
    cmdRun(sonarCommand)
  }
}

private String getProjectVersion() {
  return ''
}