/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.wrapper

import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.util.Common

/**
 * Помощник формирования команд packman
 */
class Packman {

  /**
   * Подключение существующей ИБ как рабочей
   * @param config конфигурация
   * @param credential строка авторизации
   * @return строка команды
   */
  static String setDatabase(PipelineConfiguration config, String credential) {
    def command = [
        "packman",
        "set-database", Common.getConnectionString(config),
        "%credentialID%"
    ].join(" ")
    command = command.replace("%credentialID%", credential)
    return command
  }

  /**
   * Создание файлов поставки
   * @param config конфигурация
   * @return строка команды
   */
  static String makeCf(PipelineConfiguration config) {
    def command = [
        "packman",
        "make-cf",
        "-v8version", config.v8Version
    ].join(" ")
    return command
  }

}
