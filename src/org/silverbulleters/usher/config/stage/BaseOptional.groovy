/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * Базовая реализация настроек этапа
 */
class BaseOptional {
  /**
   * Имя опции, используется в названии stage
   */
  @JsonIgnore
  String name = "Base stage"

  /**
   * Идентификатор опции
   */
  @JsonIgnore
  String id = "base"

  @JsonPropertyDescription("Таймаут времени работы этапа'")
  int timeout = 0

}
