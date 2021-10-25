/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config

import com.cloudbees.groovy.cps.NonCPS
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройка использования этапов
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Stages {
  static final EMPTY = createEmptyStages()

  @JsonIgnoreProperties("Выгрузить историю хранилища 1С с помощью утилиты `gitsync`")
  boolean gitsync = false

  @JsonIgnoreProperties("Трансформировать edt-формат конфигурации в xml")
  boolean edtTransform = false

  @JsonIgnoreProperties("Подготовить информационную базу")
  boolean prepareBase = false

  @JsonIgnoreProperties("Проверить конфигурацию с помощью синтакс-проверки")
  boolean syntaxCheck = false

  @JsonIgnoreProperties("Запустить дымовое тестирование")
  boolean smoke = false

  @JsonIgnoreProperties("Запустить TDD")
  boolean tdd = false

  @JsonIgnoreProperties("Запустить BDD")
  boolean bdd = false

  @JsonIgnoreProperties("Запустить статический анализ")
  boolean sonarqube = false

  @JsonIgnoreProperties("Собрать cf на поставке с помощью `packman`")
  boolean build = false

  @NonCPS
  private static Stages createEmptyStages() {
    return new Stages()
  }

}
