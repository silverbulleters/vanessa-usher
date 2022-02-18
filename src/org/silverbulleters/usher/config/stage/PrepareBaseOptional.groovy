/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.silverbulleters.usher.config.additional.ExtensionSource
import org.silverbulleters.usher.config.additional.Repo

/**
 * Настройки этапа подготовки информационной базы
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PrepareBaseOptional extends BaseOptional {
  @JsonPropertyDescription("Каталог с исходниками в формате xml")
  String sourcePath = "./src/cf"

  @JsonPropertyDescription("Путь к шаблону базы в формате *.dt")
  String template = ''

  @JsonPropertyDescription("Настройки подключения к хранилищу конфигурации 1С")
  Repo repo = new Repo()

  @JsonPropertyDescription("Список расширений конфигурации 1С. Используется для обновления базы")
  ExtensionSource[] extensions = []

  PrepareBaseOptional() {
    name = "Prepare base"
    id = 'prepare-base'
    timeout = 100
  }

}
