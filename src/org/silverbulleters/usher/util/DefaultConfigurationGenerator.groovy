/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.silverbulleters.usher.config.ConfigurationReader

/**
 * Генератор полной json-конфигурации pipeline
 */
class DefaultConfigurationGenerator {

  static void main(args) {
    def config = ConfigurationReader.create()
    def mapper = new ObjectMapper()

    def defaultFile = new File("resources/example.json")
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
    mapper.writeValue(defaultFile, config)
  }

}
