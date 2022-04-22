/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.stage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.silverbulleters.usher.config.additional.Repo

/**
 * Настройки этапа проверки применимости расширений
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class CheckExtensionsOptional extends BaseOptional {
    @JsonPropertyDescription("Настройки подключения к хранилищу конфигурации 1С")
    Repo repo = new Repo()

    @JsonPropertyDescription("Выполнить проверку для указанного расширения с учетом всех ранее загружаемых расширений. Если имя расширения не указано, то проверяются все расширения в порядке загрузки.")
    String extensions = ''

    CheckExtensionsOptional() {
        name = "Check extensions"
        id = 'check-extensions'
        timeout = 40
    }
}
