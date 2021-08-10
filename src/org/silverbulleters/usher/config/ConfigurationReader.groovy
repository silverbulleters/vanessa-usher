/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config

import com.cloudbees.groovy.cps.NonCPS
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.silverbulleters.usher.config.PipelineConfiguration

class ConfigurationReader {

  private static ObjectMapper mapper
  static {
    mapper = new ObjectMapper()
    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
  }

  @NonCPS
  static PipelineConfiguration create(String config) {
    return mapper.readValue(config, PipelineConfiguration.class)
  }

  @NonCPS
  static PipelineConfiguration create() {
    return new PipelineConfiguration()
  }

}
