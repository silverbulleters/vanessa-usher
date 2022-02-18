/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.wrapper

import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.GitsyncOptional

/**
 * Помощник формирования команд gitsync
 */
class Gitsync {

  /**
   * Выполнить синхронизацию хранилище 1С и git по конфигурационному файлу
   * @param config конфигурация
   * @param stageOptional настройка gitsync
   * @param credential секрет авторизации в информационной базе
   * @param credentialStorage секрет авторизации в хранилище 1С
   * @return строка команды
   */
  static String syncAll(PipelineConfiguration config, GitsyncOptional stageOptional, String credential = '', String credentialStorage = '') {

    def commandPathOne = [
        "gitsync",
        "%credentialID%",
        "--v8version", config.v8Version
    ]

    if (!stageOptional.useTemporaryInfobase) {
      commandPathOne += "--ibconnection ${config.defaultInfobase.connectionString}"
    }

    if (!stageOptional.tempPath.isEmpty()) {
      commandPathOne += "--tempdir \"${stageOptional.tempPath}\""
    }

    commandPathOne += [
        "all",
        "%credentialStorageID%",
        stageOptional.configPath
    ]

    def command = commandPathOne.join(" ")

    command = command.replace("%credentialID%", credential)
    command = command.replace("%credentialStorageID%", credentialStorage)

    return command
  }
}
