/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

class SyntaxCheckOptional extends BaseOptional {
  static final EMPTY = new SyntaxCheckOptional()

  String allurePath = "./out/syntaxCheck/allure"
  String junitPath = "./out/junit/syntaxCheck.xml"

  SyntaxCheckOptional() {
    name = "Syntax check"
    timeout = 100
  }

}
