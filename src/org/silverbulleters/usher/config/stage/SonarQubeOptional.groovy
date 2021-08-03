package org.silverbulleters.usher.config.stage

/**
 * Опции этапа SonarQube
 */
class SonarQubeOptional extends BaseOptional {
    static final EMPTY = new SonarQubeOptional()

    /**
     * Агент, на котором будет выполняться задача
     */
    String agent = "any"

    /**
     * Идентификатор sonar-scanner
     */
    String toolId = "sonar-scanner"

    /**
     * Идентификатор сервера SonarQube
     */
    String serverId = "SonarQube"

    /**
     * Режим дебага sonar-scanner
     */
    boolean debug = false

    SonarQubeOptional() {
        name = "SonarQube static analysis"
        timeout = 100
    }

}
