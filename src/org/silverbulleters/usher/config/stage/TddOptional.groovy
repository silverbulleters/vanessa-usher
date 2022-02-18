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
 * Настройки этапа TDD (Test-driven development)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class TddOptional extends BaseOptional {
  @JsonPropertyDescription("Путь к конфигурационному файлу xUnitFor1c")
  String xddConfig = "./tools/JSON/xddTestRunnerConf.json"

  @JsonPropertyDescription("Путь к каталогу или к файлу с тестами")
  String testPath = './tests/unit'

  @JsonPropertyDescription("Путь к каталогу выгрузки отчета в формате Allure")
  String allurePath = "./out/tdd/allure"

  @JsonPropertyDescription("Путь к файлу выгрузки отчета в формате jUnit")
  String junitPath = "./out/junit/xdd.xml"

  @JsonPropertyDescription("Путь к внешней обработке xddTestRunner.epf, по умолчанию ищу в пакете vanessa-add")
  String pathXUnit = ''

  TddOptional() {
    name = "TDD"
    id = 'tdd'
    timeout = 100
  }

}
