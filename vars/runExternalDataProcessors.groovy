/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.RunExternalDataProcessorsOptional
import org.silverbulleters.usher.state.PipelineState
import org.silverbulleters.usher.wrapper.VRunner

@Field
PipelineConfiguration config

@Field
PipelineState state

@Field
RunExternalDataProcessorsOptional stageOptional

/**
 * Выполнить дымовое тестирование
 * @param config конфигурация
 * @param state состояние конвейера
 */
void call(PipelineConfiguration config, RunExternalDataProcessorsOptional stageOptional, PipelineState state) {
    this.config = config
    this.state = state
    this.stageOptional = stageOptional

    infobaseHelper.unpackInfobase(config: config, state: state)

    catchError(message: 'Ошибка во время выполнения внешних обработок', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        testing()
    }

    catchError(message: 'Ошибка во время архивации отчетов о тестировании', buildResult: 'FAILURE', stageResult: 'FAILURE') {
        testResultsHelper.packTestResults(config, stageOptional, state.runExternal)
    }

}

private def testing() {
    def auth = config.defaultInfobase.auth
    if (credentialHelper.authIsPresent(auth)) {
        withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'DBUSERNAME', passwordVariable: 'DBPASSWORD')]) {
            def credential = credentialHelper.getAuthString()
            runExternal(credential)
        }
    } else {
        runExternal()
    }
}

private runExternal(String credential = '') {

    stageOptional.vrunnerAdditionals.each {source ->
        if (!source.vRunnerExecute.empty) {
            if(!stageOptional.pathEpf.isEmpty()) {
                source.vRunnerExecute = stageOptional.pathEpf.concat(source.vRunnerExecute)
            }
            def command = VRunner.runExternalDataProcessors(config, stageOptional, source.vRunnerExecute, source.vRunnerCommand)
            command = command.replace("%credentialID%", credential)
            cmdRun(command)
        }
    }
}
