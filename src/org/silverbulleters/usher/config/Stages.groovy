/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config

import com.cloudbees.groovy.cps.NonCPS
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * Настройка использования этапов
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Stages {
  @JsonPropertyDescription("Выгрузить историю хранилища 1С с помощью утилиты `gitsync`")
  boolean gitsync = false

  @JsonPropertyDescription("Трансформировать edt-формат конфигурации в xml")
  boolean edtTransform = false

  @JsonPropertyDescription("Подготовить информационную базу")
  boolean prepareBase = false

  @JsonPropertyDescription("Запустить выполнение произвольных внешних обработок 1с")
  boolean runExternal = false

  @JsonPropertyDescription("Проверить возможность применимости расширений")
  boolean checkExtensions = false

  @JsonPropertyDescription("Проверить конфигурацию с помощью синтакс-проверки")
  boolean syntaxCheck = false

  @JsonPropertyDescription("Запустить дымовое тестирование")
  boolean smoke = false

  @JsonPropertyDescription("Запустить TDD")
  boolean tdd = false

  @JsonPropertyDescription("Запустить BDD")
  boolean bdd = false

  @JsonPropertyDescription("Запустить статический анализ")
  boolean sonarqube = false

  @JsonPropertyDescription("Собрать cf на поставке с помощью `packman`")
  boolean build = false

  @JsonPropertyDescription("Запустить загрузку и обработку релизов конфигураций 1С")
  boolean yard = false
}
