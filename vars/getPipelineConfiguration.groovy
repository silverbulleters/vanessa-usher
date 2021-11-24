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
PipelineConfiguration call(String pathToConfig) {
  def file = new File(pathToConfig)
  if (!file.exists()) {
    throw new Exception("Config file not found")
  }

  def content = file.getText('UTF-8')

  println(content)

  return ConfigurationReader.create(content)
}

/**
 * Конфигурация конвейера по умолчанию
 * 
 */
PipelineConfiguration call() {
  return ConfigurationReader.create()
}
