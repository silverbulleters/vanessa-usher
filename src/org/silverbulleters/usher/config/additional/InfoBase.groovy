/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.silverbulleters.usher.UsherConstant

/**
 * Настройки подключения информационной базе
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class InfoBase {
  static final EMPTY = new InfoBase()

  @JsonPropertyDescription("Строка подключения к информационной базе, например, `/F.build/ib`")
  String connectionString = "/F.build/ib"

  @JsonPropertyDescription("Идентификатор секрета Jenkins для авторизации в информационной базе")
  String auth = UsherConstant.EMPTY_VALUE
}
