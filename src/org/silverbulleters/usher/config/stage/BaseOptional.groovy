package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnore

class BaseOptional {
    @JsonIgnore
    String name = "Base stage"

    /**
     * Таймаут работы stage
     */
    int timeout = 0

}
