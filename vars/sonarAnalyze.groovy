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

void call(PipelineConfiguration config) {
  if (!config.getStages().isSonarqube()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSonarQubeOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage('Sonarqube static analysis') {
      node(stageOptional.getAgent()) {
        def scmVars = checkout scm
        catchError(message: 'Ошибка во время выполнения sonar-scanner', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          analyze(scmVars)
        }
      }
    }
  }
}

private def analyze(scmVars) {
  def projectVersion = getProjectVersion()
  def scannerHome = tool stageOptional.getToolId()

  def sonarDebugKey = ''
  if (stageOptional.isDebug()) {
    sonarDebugKey = '-X'
  }

  def sonarBranch = ''
  if (stageOptional.isUseBranch()) {
    def branch = "${scmVars.GIT_BRANCH}"
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