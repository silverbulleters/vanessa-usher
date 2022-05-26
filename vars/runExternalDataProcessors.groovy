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
        withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            def credential = credentialHelper.getAuthString()
            runTesting(credential)
        }
    } else {
        runTesting()
    }
}

private runTesting(String credential = '') {

    int indepf = 0;
    String vRunnerCommand = ""

    for (String epf:stageOptional.vRunnerExecute) {
        if(!stageOptional.pathEpf.isEmpty()) {
            epf = stageOptional.pathEpf.concat(epf);
        }

        if(indepf < stageOptional.vRunnerCommand.size()) {
            vRunnerCommand = stageOptional.vRunnerCommand[indepf]
        }

        def command = VRunner.runExternalDataProcessors(config, stageOptional, epf, vRunnerCommand)
        command = command.replace("%credentialID%", credential)
        cmdRun(command)
        vRunnerCommand = ""
        indepf++
    }
}
