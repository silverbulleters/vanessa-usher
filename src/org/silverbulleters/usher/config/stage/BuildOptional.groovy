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
 * Настройки этапа сборки CF на поставке
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class BuildOptional extends BaseOptional {
  /* TODO: реализовать смену рабочего каталога packman */
  @JsonPropertyDescription("Путь к собранной cf на поставке")
  String distPath = '.packman/1cv8.cf'

  @JsonPropertyDescription("Прерывать этап если статус сборки равен FAILURE")
  boolean errorIfJobStatusOfFailure = false

  BuildOptional() {
    name = 'Build'
    id = 'build'
    timeout = 100
  }

}
