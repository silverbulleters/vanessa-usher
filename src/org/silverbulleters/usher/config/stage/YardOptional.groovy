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
 * Настройки этапа синхронизации релиза 1С в git-проектом
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class YardOptional extends BaseOptional {
  @JsonPropertyDescription("Имя конфигурации с сайта releases.1c.ru. Например, `EnterpriseERP20`")
  String appName = ""

  @JsonPropertyDescription("Путь к файлу описания конфигурации")
  String descriptionPath = "./description.json"

  @JsonPropertyDescription("Путь к файлу настроек yard")
  String yardSettingsPath = "./yardsettings.json"

  @JsonPropertyDescription("Идентификатор секрета для авторизации на сайте releases.1c.ru")
  String auth = ''

  @JsonPropertyDescription("Каталог запуска yard")
  String workspacePath = "./"

  @JsonPropertyDescription("Имя ветки для git push")
  String branch = "master"

  @JsonPropertyDescription("Режим включения отладочных логов yard")
  boolean debug = false

  YardOptional() {
    name = "YARD"
    id = 'yard'
    timeout = 300
  }

}
