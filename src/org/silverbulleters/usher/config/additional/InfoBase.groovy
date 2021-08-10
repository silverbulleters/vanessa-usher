/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.silverbulleters.usher.UsherConstant

@JsonIgnoreProperties(ignoreUnknown = true)
class InfoBase {
    static final EMPTY = new InfoBase()

    /**
     * Строка подключения к информационной базе
     */
    String connectionString = UsherConstant.EMPTY_VALUE
    /**
     * Идентификатор секрета авторизации в информационной базе
     */
    String auth = UsherConstant.EMPTY_VALUE
}
