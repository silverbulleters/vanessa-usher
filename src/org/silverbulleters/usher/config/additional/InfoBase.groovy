package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class InfoBase {
    static final EMPTY = new InfoBase()

    /**
     * Строка подключения к информационной базе
     */
    String connectionString = ""
    /**
     * Идентификатор секрета авторизации в информационной базе
     */
    String auth = ""
}
