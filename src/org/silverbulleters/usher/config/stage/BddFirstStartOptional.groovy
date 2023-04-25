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
 * Настройки этапа BDD-FirstStart
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class BddFirstStartOptional extends BaseOptional {
  @JsonPropertyDescription("Путь к каталогу выгрузки отчета в формате Allure. Например, `./out/bddallure`")
  String allurePath = "./out/bddallure"

  @JsonPropertyDescription("Путь к внешней обработке, по умолчанию <OneScript>/lib/add/bddRunner.epf")
  String pathVanessa = ''

  @JsonPropertyDescription("Путь к файлу настроек фреймворка тестирования ./tools/JSON/vanessaConf.json")
  String vanessasettings = './tools/JSON/vanessaFirstStartConf.json'

  BddFirstStartOptional() {
    name = 'BDD-FirstStart'
    id = 'bddFirstStart'
    timeout = 100
  }

}
