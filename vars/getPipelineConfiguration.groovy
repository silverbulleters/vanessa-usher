/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.ConfigurationReader
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.ioc.ContextRegistry

PipelineConfiguration call(String pathToConfig) {
  ContextRegistry.registerDefaultContext(this)

  if (fileExists(pathToConfig)) {
    def content = readFile(pathToConfig)
    return ConfigurationReader.create(content)
  } else {
    throw new Exception("Конфигурационный файл не найден")
  }
}