/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
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
   * Состояние выполнения произвольных внешних обработок 1с
   */
  BaseTestingState runExternal = new BaseTestingState()

  /**
   * Состояние проверки применимости расширений
   */
  BaseTestingState checkExtensions = new BaseTestingState()

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
