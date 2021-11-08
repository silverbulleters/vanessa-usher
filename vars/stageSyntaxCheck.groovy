/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.state.PipelineState

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
SyntaxCheckOptional stageOptional

def call(PipelineConfiguration config, PipelineState state) {
  if (!config.getStages().isSyntaxCheck()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSyntaxCheckOptional()
  this.state = state

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      if (config.stages.prepareBase && state.prepareBase.localBuildFolder) {
        print('Распаковка каталога "build/ib"')
        unstash 'build-ib-folder'
      }

      catchError(message: 'Ошибка во время выполнения синтаксической проверки', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        check()
      }

      catchError(message: 'Ошибка во время архивации отчетов о тестировании', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        testResultsHelper.packTestResults(config, stageOptional, state.syntaxCheck)
      }

    }
  }
}

private def check() {
  def auth = config.getDefaultInfobase().getAuth()
  if (credentialHelper.authIsPresent(auth) && credentialHelper.exist(auth)) {
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