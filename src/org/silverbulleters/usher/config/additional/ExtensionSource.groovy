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
class ExtensionSource {

  @JsonPropertyDescription("""Имя расширения конфигурации. Например, `МоеРасширениеКонфигурации`. 
  По умолчанию пустое значение и будет пропущено при обновлении информационной базы.
  """)
  String name = ''

  @JsonPropertyDescription("""Путь к исходному коду расширения 1С. По умолчанию пустое значение и будет пропущено
  при обновлении информационной базы.
  """)
  String sourcePath = ''

}
