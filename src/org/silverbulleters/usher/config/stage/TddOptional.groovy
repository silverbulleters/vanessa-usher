package org.silverbulleters.usher.config.stage

class TddOptional extends BaseOptional {
  static final EMPTY = new TddOptional()

  String xddConfig = "./tools/JSON/xddTestRunnerConf.json"
  String testPath = './tests/unit'
  String allurePath = "./out/tdd/allure"
  String junitPath = "./out/junit/xdd.xml"

  TddOptional() {
    name = "TDD"
    timeout = 100
  }

}
