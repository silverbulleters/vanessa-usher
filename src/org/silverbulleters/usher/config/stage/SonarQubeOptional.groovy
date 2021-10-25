/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройки этапа статического анализа для SonarQube
 */
class SonarQubeOptional extends BaseOptional {
  static final EMPTY = new SonarQubeOptional()

  @JsonIgnoreProperties("Имя/метки агента для запуска этапа. Например, `sonar-scanner`")
  String agent = "any"

  @JsonIgnoreProperties("Id утилиты sonar-scanner (глобальные инструменты Jenkins)")
  String toolId = "sonar-scanner"

  @JsonIgnoreProperties("Id настроек сервера SonarQube")
  String serverId = "SonarQube"

  @JsonIgnoreProperties("Режим отладки sonar-scanner")
  boolean debug = false

  @JsonIgnoreProperties("Использовать ветки при анализе")
  boolean useBranch = false

  SonarQubeOptional() {
    name = 'SonarQube static analysis'
    id = 'sonar-scanner'
    timeout = 100
  }

}
