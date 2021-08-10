/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class GitsyncOptional extends BaseOptional {
  static final EMPTY = new GitsyncOptional()

  String configPath = "./tools/JSON/gitsync_conf.JSON"

  GitsyncOptional() {
    name = "Gitsync"
    timeout = 40
  }
}
