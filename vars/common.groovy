/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration

static String getEmojiStatusForEmail(String status) {
  if (status == 'SUCCESS') {
    return "✅"
  }
  return "❌"
}

static String getEmojiStatusForSlack(String status) {
  if (status == 'SUCCESS') {
    return ":white_check_mark:"
  }
  return ":x:"
}

/**
 * Экранировать символы
 *
 * @param value произвольная строка, например строка с командой
 * @return новое экранированное значение
 */
static String shieldSymbols(String value) {
  return value.replace('$', '\\\$').replace(';', '\\;')
}

static boolean needPublishTests(PipelineConfiguration config) {
  return config.stages.syntaxCheck || config.stages.smoke || config.stages.tdd || config.stages.bdd
}

/**
 * Получить номер синхронизированной версии из файла VERSION
 *
 * @param pathToSource путь к исходному коду 1С, например, `./src/cf`
 * @return номер синхронизированной версии или пустая строка
 */
static String getRepoVersion(String pathToSource) {
  def pathToVersion = new File(pathToSource, "VERSION")
  if (!pathToVersion.exists()) {
    return ""
  }

  def version = ""

  def xmlData = new XmlParser().parse(pathToVersion)
  try {
    version = xmlData.value()[0]
  } catch (ignore) {
    // todo: нужен logger
    // не удалось прочитать файл с версией
  }

  return version
}