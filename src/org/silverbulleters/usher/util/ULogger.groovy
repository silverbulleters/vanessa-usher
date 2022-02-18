/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.util

/**
 * Утилита логирования из скриптов
 */
class ULogger {
  /**
   * Уровень логирования
   */
  static LogLevel currentLevel = LogLevel.INFO

  static void log(LogLevel level, def script, def text) {

    if (level.weight < currentLevel.weight) {
      return
    }

    switch (level) {
      case LogLevel.DEBUG:
        script.println("[DEBUG] " + text)
        break

      case LogLevel.INFO:
        script.println("[INFO] " + text)
        break

      case LogLevel.WARN:
        script.println("[WARN] " + text)
        break

      case LogLevel.ERROR:
        script.println("[ERROR] " + text)
        break

      default:
        script.println("[DEBUG] " + text)
        break
    }

  }

}
