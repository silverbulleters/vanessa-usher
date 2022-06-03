/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * Описание источника расширения конфигурации 1С
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class VrunnerAdditional {

  @JsonPropertyDescription("Имя файла внешней обработки 1с для запуска в предприятии.")
  String vRunnerExecute = ''

  @JsonPropertyDescription("""
    Cтрока, передаваемая в ПараметрыЗапуска.
    Если параметр в обработку передавать не нужно, указывается пустая строка.
    Пример: "ПараметрЗапуска1ДляОбработки1;ПараметраЗапуска2ДляОбработки1;",
   """)
  String vRunnerCommand = ''

}
