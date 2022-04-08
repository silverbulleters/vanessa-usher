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
 * Настройки этапа BDD (Behavior-driven development)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class BddOptional extends BaseOptional {
  @JsonPropertyDescription("Путь к каталогу выгрузки отчета в формате Allure. Например, `./out/bddallure`")
  String allurePath = "./out/bddallure"

  @JsonPropertyDescription("Путь к внешней обработке, по умолчанию <OneScript>/lib/add/bddRunner.epf")
  String pathVanessa = ''

  @JsonPropertyDescription("Путь к файлу настроек фреймворка тестирования ./tools/JSON/vanessaConf.json")
  String vanessasettings = './tools/JSON/vanessaConf.json'

  BddOptional() {
    name = 'BDD'
    id = 'bdd'
    timeout = 100
  }

}
