/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription

@JsonIgnoreProperties(ignoreUnknown = true)
class MatrixTesting {
  static final MatrixTesting EMPTY = new MatrixTesting()

  @JsonPropertyDescription("Агенты для запуска тестирования. Например, `windows``.")
  String[] agents = []

}
