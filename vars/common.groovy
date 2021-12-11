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
  return config.getStages().isSyntaxCheck() || config.getStages().isSmoke() || config.getStages().isTdd() || config.getStages().isBdd()
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
  } catch (e) {
    // todo: нужен logger
    // не удалось прочитать файл с версией
  }

  return version
}

/**
 * Абсолютный путь к конфигурационному файлу на master узле
 * @param pathToConfig относительный путь к конфигурационному файлу
 * @return
 */
String getAbsolutePathToConfig(String pathToConfig) {
  def jobName = getProjectPathFromWorkspace()
  def pathToBuild = "${env.JENKINS_HOME}/workspace/${jobName}@script/"
  return pathToBuild + pathToConfig
}

private String getProjectPathFromWorkspace() {
  def pathToWorkspaces = "${env.JENKINS_HOME}/workspace/workspaces.txt"
  def file = new File(pathToWorkspaces)
  if (!file.exists()) {
    return ''
  }
  
  def data = [:]
  def lines = file as String[]

  def projectName = ''
  def count = 1

  lines.each {
    if (count % 2 == 0) {
      data[projectName] = it
    } else {
      projectName = it
    }
    count++
  }

  def entry = data.find { key, value -> key == "${env.JOB_NAME}"}
  if (entry == null) {
    return ''
  } else {
    return "${entry.value}"
  }

}