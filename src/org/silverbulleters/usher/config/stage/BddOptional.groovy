package org.silverbulleters.usher.config.stage

class BddOptional extends BaseOptional {
  static final EMPTY = new BddOptional()

  String allurePath = "./out/bddallure"

  BddOptional() {
    name = "BDD"
    timeout = 100
  }

}
