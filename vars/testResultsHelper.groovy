/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.state.BaseTestingState

/**
 * Упаковать результат о тестировании
 * @param config конфигурация
 * @param stageOptional настройка шага
 * @param state состояние шага
 */
void archive(PipelineConfiguration config, stageOptional, BaseTestingState state) {
  dir(stageOptional.getAllurePath()) {
    def name = UUID.randomUUID().toString()
    state.stashes.put(name, stageOptional.getAllurePath())
    stash includes: '*', name: name
    deleteDir()
  }

  dir(config.junitPath) {
    def name = UUID.randomUUID().toString()
    state.stashes.put(name, config.junitPath)
    stash includes: "*", name: name
    deleteDir()
  }
}

/**
 * Упаковка результата о тестировании
 * @param config конфигурация пайплайна
 * @param stageOptional настройки этапа
 * @param state состояние этапа
 */
void packTestResults(PipelineConfiguration config, stageOptional, BaseTestingState state) {
  allureHelper.addCategories(stageOptional.getName(), stageOptional.getAllurePath())
  archive(config, stageOptional, state)
}

/**
 * Архивировать результаты тестирования
 * @param result
 */
void archiveTestResults(Map result) {

  result.junit.each {
    def file = new File(it)
    def directoryPath = file.getParent()
    dir(directoryPath) {
      def fileName = "junit_${file.name}"
      result.stashes.put(fileName, "out/junit")
      stash includes: "*", name: fileName
      deleteDir()
    }
  }

  result.allure.each {
    allureHelper.addCategories(result.name, it)
    dir(it) {
      def name = UUID.randomUUID().toString()
      result.stashes.put(name, it)
      stash includes: '*', name: name
      deleteDir()
    }
  }

}