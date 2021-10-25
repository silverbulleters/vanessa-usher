/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройки этапа BDD (Behavior-driven development)
 */
class BddOptional extends BaseOptional {
  static final EMPTY = new BddOptional()

  @JsonIgnoreProperties("Путь к каталогу выгрузки отчета в формате Allure. Например, `./out/bddallure`")
  String allurePath = "./out/bddallure"

  BddOptional() {
    name = 'BDD'
    id = 'bdd'
    timeout = 100
  }

}
