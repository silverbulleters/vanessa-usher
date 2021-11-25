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

/**
 * Настройки этапа выгрузки истории хранилища 1С с помощью утилиты `gitsync`
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class GitsyncOptional extends BaseOptional {
  static final EMPTY = new GitsyncOptional()

  @JsonPropertyDescription("Путь к файлу настроек")
  String configPath = "./tools/JSON/gitsync_conf.JSON"

  @JsonPropertyDescription("Идентификатор секрета Jenkins для авторизации в хранилище конфигурации")
  String auth = UsherConstant.EMPTY_VALUE

  @JsonPropertyDescription("""Использовать временную информационную базу. База будет создана во временном 
  каталоге gitsync.
  """)
  boolean useTemporaryInfobase = false

  GitsyncOptional() {
    name = 'Gitsync'
    id = 'gitsync'
    timeout = 40
  }
}
