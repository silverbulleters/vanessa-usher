/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

class BuildOptional extends BaseOptional {
  static final EMPTY = new BuildOptional()

  String distPath = '.packman/1cv8.cf'

  BuildOptional() {
    name = "Build"
    timeout = 100
  }

}
