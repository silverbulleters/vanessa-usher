/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.additional.Repo

@JsonIgnoreProperties(ignoreUnknown = true)
class PrepareBaseOptional extends BaseOptional {
  static final EMPTY = new PrepareBaseOptional()

  String sourcePath = "./src/cf"
  String template = UsherConstant.EMPTY_VALUE
  Repo repo = Repo.EMPTY

  PrepareBaseOptional() {
    name = "Prepare base"
    id = 'prepare-base'
    timeout = 100
  }
}
