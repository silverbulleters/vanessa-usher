package org.silverbulleters.usher.config.stage

class BuildOptional extends BaseOptional {
  static final EMPTY = new BuildOptional()

  String distPath = './.packman/1cv8.cf'

  BuildOptional() {
    name = "Build"
    timeout = 100
  }

}
