/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package utils

import com.lesfurets.jenkins.unit.BasePipelineTest

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.ProjectSource.projectSource

/**
 * Хелпер классов тестирования
 */
class TestHelper {

  /**
   * Зарегистрировать shared library из текущего проекта
   * @param baseTest базовый тест pipeline
   */
  static void registerUsher2(BasePipelineTest baseTest) {
    def library = library().name('usher2')
        .defaultVersion('<notNeeded>')
        .allowOverride(true)
        .implicit(true)
        .targetPath('<notNeeded>')
        .retriever(projectSource())
        .build()
    baseTest.helper.registerSharedLibrary(library)
  }

}
