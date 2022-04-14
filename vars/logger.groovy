/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.util.LogLevel
import org.silverbulleters.usher.util.ULogger

/**
 * Установить уровень логирования. Например, для включения отладки нужно передать значение LogLevel.DEBUG
 * @param level уровень логирования, по умолчанию LogLevel.INFO
 */
void setLevel(LogLevel level) {
  ULogger.currentLevel = level
}

/**
 * Вывести отладочное сообщение
 * @param text сообщение
 */
void debug(def text) {
  log(LogLevel.DEBUG, text)
}

/**
 * Вывести информационное сообщение
 * @param text сообщение
 */
void info(def text) {
  log(LogLevel.INFO, text)
}

/**
 * Вывести предупреждающее сообщение
 * @param text сообщение
 */
void warn(def text) {
  log(LogLevel.WARN, text)
}

/**
 * Вывести сообщение с ошибкой
 * @param text сообщение
 */
void error(def text) {
  log(LogLevel.ERROR, text)
}

/**
 * Вывести сообщение с указанным уровнем логирования
 *
 * @param level уровень логирования
 * @param text сообщение
 */
void log(LogLevel level, def text) {
  try {
    ULogger.log(level, this, text)
  } catch (ignore) {
  }
}
