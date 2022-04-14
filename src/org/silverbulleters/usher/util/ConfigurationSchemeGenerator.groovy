/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator
import org.silverbulleters.usher.config.PipelineConfiguration

/**
 * Генератор json-схемы pipeline
 */
class ConfigurationSchemeGenerator {

  static void main(args) {
    def mapper = new ObjectMapper()
    def generator = new JsonSchemaGenerator(mapper)
    def jsonSchema = generator.generateSchema(PipelineConfiguration.class)

    def jsonSchemaFile = new File("resources/schema.json")
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
    mapper.writeValue(jsonSchemaFile, jsonSchema)
  }

}
