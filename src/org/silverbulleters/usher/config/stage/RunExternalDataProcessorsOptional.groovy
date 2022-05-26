/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * Настройки этапа
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class RunExternalDataProcessorsOptional extends BaseOptional {

    @JsonPropertyDescription("""
     Имена файлов внешних обработок 1с для запуска в предприятии.
     Пример: ["Обработка1.epf", "Обрабортка2.epf", "Обработка3.epf"]
  """)
    String[] vRunnerExecute = []

    @JsonPropertyDescription("""
    Cтрока, передаваемая в ПараметрыЗапуска.
    Массив строк, указываются по аналогии с параметром vRunnerExecute.
    Если параметр в обработку передавать не нужно, указывается пустая строка.
    Пример: ["ПараметрЗапуска1ДляОбработки1;ПараметраЗапуска2ДляОбработки1;", "", "ПараметрЗапуска1ДляОбработки3"]
   """)
    String[] vRunnerCommand = []

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
