/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Настройки подключения к хранилищу конфигурации 1С
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Repo {
  @JsonIgnoreProperties("Путь к хранилищу конфигурации. Например, `tcp://repo-server/repo`")
  String path = ''

  @JsonIgnoreProperties("Идентификатор секрета Jenkins для авторизации в хранилище конфигурации")
  String auth = ''

  boolean isEmpty() {
    return !path.isEmpty()
  }
}
