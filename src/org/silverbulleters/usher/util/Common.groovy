/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.util

import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.PipelineConfiguration

/**
 * Вспомогательный класс
 */
class Common {

  /**
   * Получить версию библиотеки
   * @return версия библиотеки
   */
  static String getLibraryVersion() {
    return UsherConstant.PROJECT_VERSION
  }

  /**
   * Получить sha коммита из лога
   * @param log лог сборки
   * @return sha коммита
   */
  static String getShaCommitFromLog(def log) {
    def matcher = (log =~ /Obtained.+?from.(\w{40})/)
    if (matcher.find()) {
      return matcher[0][1]
    }
    return ''
  }

  static String getConnectionString(PipelineConfiguration config) {
    return config.defaultInfobase.connectionString
  }

}
