/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

class TddOptional extends BaseOptional {
  static final EMPTY = new TddOptional()

  String xddConfig = "./tools/JSON/xddTestRunnerConf.json"
  String testPath = './tests/unit'
  String allurePath = "./out/tdd/allure"
  // FIXME: общий путь к junit
  String junitPath = "./out/junit/xdd.xml"

  TddOptional() {
    name = "TDD"
    timeout = 100
  }

}
