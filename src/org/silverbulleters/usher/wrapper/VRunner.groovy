/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.wrapper

import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.ExtensionSource
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.config.stage.RunExternalDataProcessorsOptional
import org.silverbulleters.usher.config.stage.CheckExtensionsOptional
import org.silverbulleters.usher.config.stage.PrepareBaseOptional
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.config.stage.TddOptional
import org.silverbulleters.usher.util.Common

/**
 * Помощник формирования команд vanessa-runner
 */
class VRunner {

  /**
   * Инициализировать информационную базу из исходного кода
   * @param config конфигурация
   * @param optional настройки prepareBase
   * @return строка команды
   */
  static def initDevFromSource(PipelineConfiguration config, PrepareBaseOptional optional) {
    def command = [
        "vrunner",
        "init-dev",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--src=${optional.sourcePath}",
        "--v8version", config.v8Version,
        "--nocacheuse"
    ]
    return command.join(" ")
  }

  /**
   * Инициализировать информационную базу из шаблона dt
   * @param config конфигурация
   * @param optional настройки prepareBase
   * @return строка команды
   */
  static def initDevWithTemplate(PipelineConfiguration config, PrepareBaseOptional optional) {
    // TODO: проверить существование `optional.template`
    def command = [
        "vrunner",
        "init-dev",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--dt", optional.template,
        "--v8version", config.v8Version,
        "--nocacheuse"
    ]
    return command.join(" ")
  }

  /**
   * Загрузить конфигурацию из исходного кода
   * @param config конфигурация
   * @param optional настройка prepareBase
   * @return строка команды
   */
  static def compile(PipelineConfiguration config, PrepareBaseOptional optional) {
    def command = [
        "vrunner",
        "compile",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--src=${optional.sourcePath}",
        "-c",
        "--noupdate",
        "--v8version", config.v8Version,
        "--nocacheuse"
    ]
    return command.join(" ")
  }

  /**
   * Загрузить расширение конфигурации из исходного кода
   * @param config конфигурация
   * @param source описание расширения
   * @return строка команды
   */
  static def compileExt(PipelineConfiguration config, ExtensionSource source) {

    def command = [
        "vrunner",
        "compileext",
        source.sourcePath,
        source.name,
        "--updatedb",
        "%credentialID%",
        "--ibconnection", Common.getConnectionString(config),
        "--v8version", config.v8Version,
        "--settings", config.vrunnerConfig,
        "--nocacheuse"
    ]

    return command.join(" ")
  }

  /**
   * Загрузить конфигурацию из хранилища 1С
   * @param config конфигурация
   * @param optional настройка prepareBase
   * @param repoVersion номер версии конфигурации в хранилище 1С
   * @return строка команды
   */
  static def loadRepo(PipelineConfiguration config, PrepareBaseOptional optional, String repoVersion = '') {
    def command = [
        "vrunner",
        "loadrepo",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "%credentialStorageID%",
        "--v8version", config.v8Version,
        "--storage-name", optional.repo.path,
        "--nocacheuse"
    ]

    // FIXME: пробросить изменения по repoVersion
    if (!repoVersion.empty) {
      command += "--storage-ver ${repoVersion}"
    }

    return command.join(" ")
  }

  /**
   * Обновить информационную базу
   * @param config конфигурация
   * @param optional настройка prepareBase
   * @return строка команды
   */
  static def updateDB(PipelineConfiguration config, PrepareBaseOptional optional) {
    def command = [
        "vrunner",
        "updatedb",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--v8version", config.v8Version,
        "--nocacheuse"
    ]
    return command.join(" ")
  }

  /**
   * Запустить "типовое" мигрирование после обновление информационной базы 1С
   * @param config конфигурация
   * @param optional настройка prepareBase
   * @return строка команды
   */
  static def migrate(PipelineConfiguration config, PrepareBaseOptional optional) {
    def command = [
        "vrunner",
        "run",
        "%credentialID%",
        "--ibconnection", Common.getConnectionString(config),
        "--settings", config.vrunnerConfig,
        "--v8version", config.v8Version,
        "--command", "'ЗапуститьОбновлениеИнформационнойБазы;ЗавершитьРаботуСистемы;'",
        "--execute", '"$runnerRoot/epf/ЗакрытьПредприятие.epf"'
    ]
    return command.join(" ")
  }

  /**
   * Запустить синтаксическую проверку конфигурации через конфигуратор 1С
   * @param args настройки
   * @return строка команды
   */
  static def syntaxCheck(Map args) {
    def config = args.config as PipelineConfiguration
    def setting = args.setting as SyntaxCheckOptional

    def command = [
        "vrunner",
        "syntax-check",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--allure-results2", args.allurePath,
        "--junitpath", args.junitPath,
        "--ibconnection", Common.getConnectionString(config),
        "--v8version", config.v8Version
    ]

    if (args.existsExceptionFile) {
      command += "--exception-file ${setting.exceptionFile}"
    }

    if (setting.groupByMetadata) {
      command += "--groupbymetadata"
    }

    command += "--mode"
    command += setting.mode.join(" ")
    if (args.checkExtensions) {
      command += "-AllExtensions"
    }

    return command.join(" ")
  }

  /**
   * Запустить дымовое тестирование
   * @param config конфигурация
   * @param optional настройка smoke
   * @return строка команды
   */
  static def smoke(PipelineConfiguration config, SmokeOptional optional) {
    def pathToAllure = "${optional.allurePath}/allure-smoke.xml"
    def command = [
        "vrunner",
        "xunit",
        optional.testPath,
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--v8version", config.v8Version,
        "--testclient", "%credentialTestClientID%",
        "--reportsxunit", "\"ГенераторОтчетаJUnitXML{${optional.junitPath}};ГенераторОтчетаAllureXMLВерсия2{${pathToAllure}}\"",
        "--xddExitCodePath", "./xddExitCodePath.txt",
        "--xddConfig", optional.xddConfig
    ]

    if(!optional.pathXUnit.isEmpty()) {
      command += "--pathxunit \"${optional.pathXUnit}\""
    }

    return command.join(" ")
  }

  /**
   * Запустить xunit тестирование
   * @param config конфигурация
   * @param optional настройка tdd
   * @return строка команды
   */
  static def xunit(PipelineConfiguration config, TddOptional optional) {
    def pathToAllure = "${optional.allurePath}/allure-xdd.xml"
    def command = [
        "vrunner",
        "xunit",
        optional.testPath,
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--v8version", config.v8Version,
        "--testclient", "%credentialTestClientID%",
        "--reportsxunit", "\"ГенераторОтчетаJUnitXML{${optional.junitPath}};ГенераторОтчетаAllureXMLВерсия2{${pathToAllure}}\"",
        "--xddExitCodePath", "./xddExitCodePath.txt",
        "--xddConfig", optional.xddConfig
    ]

    if(!optional.pathXUnit.isEmpty()) {
      command += "--pathxunit \"${optional.pathXUnit}\""
    }

    return command.join(" ")
  }

  /**
   * Запустить поведенческое тестирование
   * @param config конфигурация
   * @param optional настройки bdd
   * @return строка команды
   */
  static def vanessa(PipelineConfiguration config, BddOptional optional) {
    def command = [
        "vrunner",
        "vanessa",
        "%credentialID%",
        "--settings", config.vrunnerConfig,
        "--ibconnection", Common.getConnectionString(config),
        "--v8version", config.v8Version
    ]

    if(!optional.pathVanessa.isEmpty()) {
      command += "--pathvanessa \"${optional.pathVanessa}\""
    }

    if(!optional.vanessasettings.isEmpty()) {
      command += "--vanessasettings \"${optional.vanessasettings}\""
    }

    return command.join(" ")
  }

  /**
   * Выполнить проверку применимости расширений
   * @param config конфигурация
   * @param optional настройки этапа
   */
  static def checkCanApplyExtensions(PipelineConfiguration config, CheckExtensionsOptional optional) {
    def command = [
            "vrunner",
            "designer",
            "--v8version", config.v8Version,
            "%credentialID%",
            "--ibconnection", Common.getConnectionString(config),
            "--nocacheuse",
            "%credentialStorageID%",
            "--storage-name", optional.repo.path
    ]

    command += "--additional \"/CheckCanApplyConfigurationExtensions"


    if (!optional.extensions.isEmpty()) {
      command += "-Extension ${optional.extensions}\""
    }
    else {
      command += "\""
    }

    return command.join(" ")
  }

  /**
   * Выполнить произовльные внешние обработки
   * @param config конфигурация
   * @param optional настройки
   * @param epf имя файла обработки
   * @param vRunnerCommand cтрока, передаваемая в ПараметрыЗапуска
   * @return строка команды
   */
  static def runExternalDataProcessors(PipelineConfiguration config, RunExternalDataProcessorsOptional optional, epf, vRunnerCommand = '') {
    def command = [
            "vrunner",
            "run",
            "%credentialID%",
            "--ibconnection", Common.getConnectionString(config),
            "--settings", optional.settings,
            "--v8version", config.v8Version,
            "--execute", "\"${epf}\"",
    ]

    if(!vRunnerCommand.isEmpty()) {
      command += "--command \'${vRunnerCommand}\'"
    }
    return  command.join(" ")

  }
}
