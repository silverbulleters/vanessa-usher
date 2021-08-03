package org.silverbulleters.usher.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator
import org.silverbulleters.usher.config.PipelineConfiguration

class ConfigurationSchemeGenerator {

  static void main(args) {
    def mapper = new ObjectMapper()
    def generator = new JsonSchemaGenerator(mapper);
    def jsonSchema = generator.generateSchema(PipelineConfiguration.class);

    def json = new StringWriter();
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.writeValue(json, jsonSchema);

    def jsonSchemaFile = new File("resources/schema.json");
    mapper.writeValue(jsonSchemaFile, jsonSchema);
  }

}
