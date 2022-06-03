/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.silverbulleters.usher.config.additional.VrunnerAdditional

/**
 * Настройки этапа
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class RunExternalDataProcessorsOptional extends BaseOptional {

    @JsonPropertyDescription("Массив из пары vrunnercommand и vrunnerexecute")
    VrunnerAdditional[] vrunnerAdditionals = []

    @JsonPropertyDescription("Путь к каталогу с обработками, по умолчанию ./tools/epf/")
    String pathEpf = "./tools/epf/"

    @JsonPropertyDescription("Путь к файлу настроек Vanessa-runner в формате json. По умолчанию ./tools/JSON/vRunnerExternalOptions.JSON")
    String settings = "./tools/JSON/vRunnerExternalOptions.JSON"

    @JsonPropertyDescription("Путь к каталогу выгрузки отчета в формате Allure")
    String allurePath = "./out/runExternal/allure"

    @JsonPropertyDescription("Путь к файлу выгрузки отчета в формате jUnit")
    String junitPath = "./out/junit/runExternal.xml"

    RunExternalDataProcessorsOptional() {
        name = 'Run external data processors'
        id = 'runExternal'
        timeout = 100
    }

}
