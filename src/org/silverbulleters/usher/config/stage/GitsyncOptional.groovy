package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.silverbulleters.usher.config.additional.Repo

@JsonIgnoreProperties(ignoreUnknown = true)
class GitsyncOptional extends BaseOptional {
    static final EMPTY = new GitsyncOptional()

    Repo repo = Repo.EMPTY
    String configPath = "./tools/JSON/gitsync_conf.JSON"

    GitsyncOptional() {
        name = "Gitsync"
        timeout = 40
    }
}
