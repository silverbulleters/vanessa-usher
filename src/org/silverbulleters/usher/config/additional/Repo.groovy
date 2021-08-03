package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Repo {
    static final Repo EMPTY = new Repo()

    String path = ""
    String auth = ""
}
