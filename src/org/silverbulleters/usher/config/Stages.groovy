package org.silverbulleters.usher.config

import com.cloudbees.groovy.cps.NonCPS
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Stages {
    static final EMPTY = createEmptyStages()

    boolean gitsync = false
    boolean prepareBase = false
    boolean syntaxCheck = false
    boolean smoke = false
    boolean tdd = false
    boolean bdd = false
    boolean sonarqube = false
    boolean build = false
    boolean deploy = false

    @NonCPS
    private static Stages createEmptyStages() {
        return new Stages()
    }

}
