/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher

/**
 * Информация для уведомления
 */
class NotificationInfo {
  /**
   * Id канала slack
   */
  String channelId = ""

  /**
   * Статус сборки
   */
  String status = ""

  /**
   * Имя проекта
   */
  String projectName = ""

  /**
   * Номер сборки
   */
  String buildNumber = ""

  /**
   * Url-сслыка на сборку
   */
  String buildUrl = ""

  /**
   * Имя ветки
   */
  String branchName = "<Не заполнено>"

  /**
   * Хеш-коммита
   */
  String commitId = "<Не заполнено>"
  /**
   * Текст коммита
   */
  String commitMessage = "<Не заполнено>"

  /**
   * Текст ошибки
   */
  String errorMessage = ""

  /**
   * Показывать результаты тестирования
   */
  boolean showTestResults = false

  /**
   * Количество успешных тестов
   */
  int successCount = 0

  /**
   * Количество упавших тестов
   */
  int failedCount = 0

  /**
   * Количество пропущенных тестов
   */
  int skippedCount = 0
}
