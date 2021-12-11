/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
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
PipelineConfiguration call(String pathToConfig, boolean fromNode) {
  if (fromNode) {
    return readConfigurationFromNode(pathToConfig)
  } else {
    readConfigurationFromWorkspace(pathToConfig)
  }
}

PipelineConfiguration call(String pathToConfig) {
  return getPipelineConfiguration(pathToConfig, false)
}

/**
 * Конфигурация конвейера по умолчанию
 *
 */
PipelineConfiguration call() {
  return ConfigurationReader.create()
}

private PipelineConfiguration readConfigurationFromNode(String pathToConfig) {

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

private PipelineConfiguration readConfigurationFromWorkspace(String pathToConfig) {
  def file = new File(pathToConfig)
  if (!file.exists()) {

    logger.error("Не удалось прочитать конфигурационный файл проекта")

    throw new Exception("Config file not found")
  }
  def content = file.getText('UTF-8')

  logger.debug("""Конфигурационный файл:
  ${content}
  """)

  return ConfigurationReader.create(content)
}