/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

/**
 * Опции этапа SonarQube
 */
class SonarQubeOptional extends BaseOptional {
  static final EMPTY = new SonarQubeOptional()

  /**
   * Агент, на котором будет выполняться задача
   */
  String agent = "any"

  /**
   * Идентификатор sonar-scanner
   */
  String toolId = "sonar-scanner"

  /**
   * Идентификатор сервера SonarQube
   */
  String serverId = "SonarQube"

  /**
   * Режим дебага sonar-scanner
   */
  boolean debug = false

  /**
   * Использовать ветки
   */
  boolean useBranch = false

  SonarQubeOptional() {
    name = 'SonarQube static analysis'
    id = 'sonar-scanner'
    timeout = 100
  }

}
