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
 * Настройки этапа дымового тестирования
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class SmokeOptional extends BaseOptional {
  @JsonPropertyDescription("Путь к конфигурационному файлу xUnitFor1c")
  String xddConfig = "./tools/JSON/smokeTestRunnerConf.json"

  @JsonPropertyDescription("Путь к каталогу / к файлу с тестами, или название подсистемы")
  String testPath = '$addroot/tests/smoke'

  @JsonPropertyDescription("Путь к каталогу выгрузки отчета в формате Allure")
  String allurePath = "./out/smoke/allure"

  @JsonPropertyDescription("Путь к файлу выгрузки отчета в формате jUnit")
  String junitPath = "./out/junit/smoke.xml"

  @JsonPropertyDescription("Путь к внешней обработке xddTestRunner.epf, по умолчанию ищу в пакете vanessa-add")
  String pathXUnit = ''

  SmokeOptional() {
    name = 'Smoke'
    id = 'smoke'
    timeout = 100
  }

}
