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
 * Настройка этапа трансформации edt-формата конфигурации в xml
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class EdtTransformOptional extends BaseOptional {
  @JsonPropertyDescription("""Модуль edt для утилиты ring. По умолчанию используется `edt`.
  Для использования, например, версии 2021.2 нужно указать `edt@2021.2.0`.
  """)
  String edt = "edt"

  @JsonPropertyDescription("Каталог рабочей области проекта")
  String workspacePath = "./build/workspace"

  @JsonPropertyDescription("Каталог edt-выгрузки конфигурации")
  String sourcePath = "./src/cf"

  @JsonPropertyDescription("Каталог xml-выгрузки конфигурации")
  String outPath = "./build/out"

  EdtTransformOptional() {
    name = 'EDT transform to XML'
    id = 'edt-transform'
    timeout = 30
  }

}
