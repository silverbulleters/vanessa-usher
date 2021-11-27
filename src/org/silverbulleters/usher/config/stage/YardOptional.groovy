/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.silverbulleters.usher.UsherConstant

@JsonIgnoreProperties(ignoreUnknown = true)
class YardOptional extends BaseOptional {
  static final EMPTY = new YardOptional()

  @JsonPropertyDescription("Имя конфигурации с сайта releases.1c.ru. Например, `EnterpriseERP20`")
  String appName = ""

  @JsonPropertyDescription("Путь к файлу описания конфигурации")
  String descriptionPath = "./description.json"

  @JsonPropertyDescription("Путь к файлу настроек yard")
  String yardSettingsPath = "./yardsettings.json"

  @JsonPropertyDescription("Секрет для авторизации на сайте releases.1c.ru")
  String auth = UsherConstant.EMPTY_VALUE

  @JsonPropertyDescription("Каталог запуска yard")
  String workspacePath = "./"

  @JsonPropertyDescription("Имя ветки для git push")
  String branch = "master"

  @JsonPropertyDescription("Режим дебага yard")
  boolean debug = false

  YardOptional() {
    name = "YARD"
    id = 'yard'
    timeout = 300
  }

}
