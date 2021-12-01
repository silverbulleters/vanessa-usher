/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.state.BaseTestingState

void archive(PipelineConfiguration config, stageOptional, BaseTestingState state) {
  dir(stageOptional.getAllurePath()) {
    def name = UUID.randomUUID().toString()
    state.stashes.put(name, stageOptional.getAllurePath())
    stash includes: '*', name: name
    deleteDir()
  }

  dir(config.getJunitPath()) {
    def name = UUID.randomUUID().toString()
    state.stashes.put(name, config.getJunitPath())
    stash includes: "*", name: name
    deleteDir()
  }
}

/**
 * Упаковка результата о тестировании
 * @param config конфигурация пайплайна
 * @param stageOptional - настройки этапа
 * @param state - состояние этапа
 */
void packTestResults(PipelineConfiguration config, stageOptional, BaseTestingState state) {
  allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
  archive(config, stageOptional, state)
}

/**
 * Архивация результатов тестирования
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
    allureHelper.createAllureCategories(result.name, it)
    dir(it) {
      def name = UUID.randomUUID().toString()
      result.stashes.put(name, it)
      stash includes: '*', name: name
      deleteDir()
    }
  }

}