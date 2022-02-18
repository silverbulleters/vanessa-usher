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
 * Настройки этапа синтакс-проверки конфигурации 1С
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class SyntaxCheckOptional extends BaseOptional {
  @JsonPropertyDescription("Путь к каталогу выгрузки отчета в формате Allure")
  String allurePath = "./out/syntaxCheck/allure"

  @JsonPropertyDescription("Путь к файлу выгрузки отчета в формате jUnit")
  String junitPath = "./out/junit/syntaxCheck.xml"

  @JsonPropertyDescription("Проверять все расширения в информационной базе")
  boolean checkExtensions = false

  @JsonPropertyDescription("Группировать результат проверки по метаданным")
  boolean groupByMetadata = true

  @JsonPropertyDescription("Список параметров проверки")
  String[] mode = [
      "-ThinClient",
      "-Server",
      "-ConfigLogIntegrity",
      "-HandlersExistence",
      "-ExtendedModulesCheck"
  ]

  @JsonPropertyDescription("Путь к файлу с исключениями")
  String exceptionFile = "./tools/syntax-check/exceptionFile.txt"

  SyntaxCheckOptional() {
    name = 'Syntax check'
    id = 'syntax'
    timeout = 100
  }

}
