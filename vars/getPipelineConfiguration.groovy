/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.ConfigurationReader
import org.silverbulleters.usher.config.PipelineConfiguration

/**
 * Конфигурация конвейера из файла
 *
 * @param pathToConfig путь к конфигурации
 * @return
 */
PipelineConfiguration call(String pathToConfig) {
  return readConfigurationFromNode(pathToConfig)
}

/**
 * Конфигурация конвейера по умолчанию
 *
 */
PipelineConfiguration call() {
  return ConfigurationReader.create()
}

private def readConfigurationFromNode(String pathToConfig) {

  if (fileExists(pathToConfig)) {
    def content = readFile(pathToConfig)

    logger.debug("""Конфигурационный файл:
    ${content}
    """)

    return ConfigurationReader.create(content)
  } else {

    logger.error("Не удалось прочитать конфигурационный файл проекта")

    throw new Exception("Конфигурационный файл не найден")
  }

}
