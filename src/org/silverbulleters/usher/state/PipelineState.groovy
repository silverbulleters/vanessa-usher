/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.state;

/**
 * Состояние пайплайна
 */
class PipelineState {
  /**
   * Состояние этапа prepareBase
   */
  PrepareBaseState prepareBase = new PrepareBaseState()

  /**
   * Состояние синтакс-проверки
   */
  BaseTestingState syntaxCheck = new BaseTestingState()

  /**
   * Состояние дымовых тестирования
   */
  BaseTestingState smoke = new BaseTestingState()

  /**
   * Состояние tdd тестирования
   */
  BaseTestingState tdd = new BaseTestingState()

  /**
   * Состояние bdd тестирования
   */
  BaseTestingState bdd = new BaseTestingState()
}
