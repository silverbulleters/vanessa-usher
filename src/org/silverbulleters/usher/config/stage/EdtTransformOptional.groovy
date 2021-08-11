package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class EdtTransformOptional extends BaseOptional {
  static final EMPTY = new EdtTransformOptional()

  String edt = "edt"
  String workspacePath = "./build/workspace"
  String sourcePath = "./src/cf"
  String outPath = "./build/out"

  EdtTransformOptional() {
    name = 'EDT transform to XML'
    id = 'edt-transform'
    timeout = 30
  }

}
