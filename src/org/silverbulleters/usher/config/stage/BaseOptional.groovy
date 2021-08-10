/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnore

class BaseOptional {
  @JsonIgnore
  String name = "Base stage"

  /**
   * Таймаут работы stage
   */
  int timeout = 0
}
