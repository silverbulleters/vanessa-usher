/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройки этапа сборки CF на поставке
 */
class BuildOptional extends BaseOptional {
  static final EMPTY = new BuildOptional()

  /* TODO: реализовать смену рабочего каталога packman */
  @JsonIgnoreProperties("Путь к собранной cf на поставке")
  String distPath = '.packman/1cv8.cf'

  BuildOptional() {
    name = 'Build'
    id = 'build'
    timeout = 100
  }

}
