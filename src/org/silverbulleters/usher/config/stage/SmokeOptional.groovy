/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

class SmokeOptional extends BaseOptional {
  static final EMPTY = new SmokeOptional()

  String xddConfig = "./tools/JSON/smokeTestRunnerConf.json"
  String testPath = '$addroot/tests/smoke'
  String allurePath = "./out/smoke/allure"
  String junitPath = "./out/junit/smoke.xml"

  SmokeOptional() {
    name = 'Smoke'
    id = 'smoke'
    timeout = 100
  }

}
