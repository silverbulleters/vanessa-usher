/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
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
