/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

class BddOptional extends BaseOptional {
  static final EMPTY = new BddOptional()

  String allurePath = "./out/bddallure"

  BddOptional() {
    name = 'BDD'
    id = 'bdd'
    timeout = 100
  }

}
