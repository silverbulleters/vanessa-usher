/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.util

/**
 * Уровень логирования
 */
enum LogLevel {
  DEBUG(0),
  INFO(1),
  WARN(2),
  ERROR(3)

  LogLevel(int weight) {
    this.weight = weight
  }

  int weight
}
