/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional

@Field
PipelineConfiguration config

@Field
SyntaxCheckOptional stageOptional

def call(PipelineConfiguration config) {
  if (!config.getStages().isSyntaxCheck()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSyntaxCheckOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения синтаксической проверки', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          check()
        }
        if (fileExists(stageOptional.getAllurePath())) {
          allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
        }
      }
    }
  }
}

private def check() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getAuthString()
      runSyntaxCheck(credential)
    }
  } else {
    runSyntaxCheck('')
  }
}

private def runSyntaxCheck(credential) {
  command = vrunner.syntaxCheck(config, stageOptional)
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}