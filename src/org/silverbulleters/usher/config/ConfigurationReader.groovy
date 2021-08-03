package org.silverbulleters.usher.config

import com.cloudbees.groovy.cps.NonCPS
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.silverbulleters.usher.config.JobConfiguration

class ConfigurationReader {

    private static ObjectMapper mapper
    static {
        mapper = new ObjectMapper()
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @NonCPS
    static JobConfiguration create(String config) {
        return mapper.readValue(config, JobConfiguration.class)
    }

    static JobConfiguration create() {
        return new JobConfiguration()
    }

}
