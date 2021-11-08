/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.silverbulleters.usher.UsherConstant

/**
 * Настройки подключения к хранилищу конфигурации 1С
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Repo {
  static final Repo EMPTY = new Repo()

  @JsonIgnoreProperties("Путь к хранилищу конфигурации. Например, `tcp://repo-server/repo`")
  String path = UsherConstant.EMPTY_VALUE

  @JsonIgnoreProperties("Идентификатор секрета Jenkins для авторизации в хранилище конфигурации")
  String auth = UsherConstant.EMPTY_VALUE
}
