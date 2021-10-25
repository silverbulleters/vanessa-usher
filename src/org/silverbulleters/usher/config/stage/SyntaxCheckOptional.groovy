/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройки этапа синтакс-проверки конфигурации 1С
 */
class SyntaxCheckOptional extends BaseOptional {
  static final EMPTY = new SyntaxCheckOptional()

  @JsonIgnoreProperties("Путь к каталогу выгрузки отчета в формате Allure")
  String allurePath = "./out/syntaxCheck/allure"

  @JsonIgnoreProperties("Путь к файлу выгрузки отчета в формате jUnit")
  String junitPath = "./out/junit/syntaxCheck.xml"

  SyntaxCheckOptional() {
    name = 'Syntax check'
    id = 'syntax'
    timeout = 100
  }

}
