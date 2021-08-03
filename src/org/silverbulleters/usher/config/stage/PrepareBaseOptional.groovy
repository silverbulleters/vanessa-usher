package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.silverbulleters.usher.config.additional.Repo

@JsonIgnoreProperties(ignoreUnknown = true)
class PrepareBaseOptional extends BaseOptional {
    static final EMPTY = new PrepareBaseOptional()

    String sourcePath = "./src/cf"
    String template = ""
    Repo repo = Repo.EMPTY

    PrepareBaseOptional() {
        name = "Prepare base"
        timeout = 100
    }
}
