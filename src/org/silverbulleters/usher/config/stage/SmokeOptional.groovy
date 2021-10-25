/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройки этапа дымового тестирования
 */
class SmokeOptional extends BaseOptional {
  static final EMPTY = new SmokeOptional()

  @JsonIgnoreProperties("Путь к конфигурационному файлу xUnitFor1c")
  String xddConfig = "./tools/JSON/smokeTestRunnerConf.json"

  @JsonIgnoreProperties("Путь к каталогу или к файлу с тестами")
  String testPath = '$addroot/tests/smoke'

  @JsonIgnoreProperties("Путь к каталогу выгрузки отчета в формате Allure")
  String allurePath = "./out/smoke/allure"

  @JsonIgnoreProperties("Путь к файлу выгрузки отчета в формате jUnit")
  String junitPath = "./out/junit/smoke.xml"

  SmokeOptional() {
    name = 'Smoke'
    id = 'smoke'
    timeout = 100
  }

}
