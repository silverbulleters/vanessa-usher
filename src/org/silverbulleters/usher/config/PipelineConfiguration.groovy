/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.additional.NotificationOptional
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.config.stage.BuildOptional
import org.silverbulleters.usher.config.stage.EdtTransformOptional
import org.silverbulleters.usher.config.stage.GitsyncOptional
import org.silverbulleters.usher.config.stage.PrepareBaseOptional
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.config.stage.SonarQubeOptional
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.config.stage.TddOptional

/**
 * Настройки pipeline
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PipelineConfiguration implements Serializable {

  @JsonIgnoreProperties("Версия платформы 1С. Например, `8.3.20.1549`")
  String v8Version = "8.3"

  @JsonIgnoreProperties("""Имя/метки агента для запуска этапа. Например, `sonar-scanner`. 
  Используется для всех этапов, кроме `sonarqube`.
  """)
  String agent = "any"

  @JsonIgnoreProperties("Режим отладки")
  boolean debug = false

  @JsonIgnoreProperties("Настройки уведомлений")
  NotificationOptional notification = NotificationOptional.EMPTY

  @JsonIgnoreProperties("Путь к конфигурационному файлу vanessa-runner")
  String vrunnerConfig = "./tools/JSON/vRunner.json"

  @JsonIgnoreProperties("Путь к каталогу с отчетами в в формате jUnit")
  String junitPath = UsherConstant.JUNIT_PATH

  @JsonIgnoreProperties("Общий таймаут на время работы pipeline")
  int timeout = 100

  @JsonIgnoreProperties("Информационная база по умолчанию")
  InfoBase defaultInfobase = InfoBase.EMPTY

  @JsonIgnoreProperties("Настройка использования этапов")
  Stages stages = Stages.EMPTY

  @JsonIgnoreProperties("Настройки этапа выгрузки истории хранилища 1С с помощью утилиты `gitsync`")
  @JsonProperty("gitsync")
  GitsyncOptional gitsyncOptional = GitsyncOptional.EMPTY

  @JsonIgnoreProperties("Настройка этапа трансформации edt-формата конфигурации в xml")
  @JsonProperty("edtTransform")
  EdtTransformOptional edtTransformOptional = EdtTransformOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа подготовки информационной базы")
  @JsonProperty("preparebase")
  PrepareBaseOptional prepareBaseOptional = PrepareBaseOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа синтакс-проверки конфигурации 1С")
  @JsonProperty("syntaxCheck")
  SyntaxCheckOptional syntaxCheckOptional = SyntaxCheckOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа дымового тестирования")
  @JsonProperty("smoke")
  SmokeOptional smokeOptional = SmokeOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа TDD (Test-driven development)")
  @JsonProperty("tdd")
  TddOptional tddOptional = TddOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа BDD (Behavior-driven development)")
  @JsonProperty("bdd")
  BddOptional bddOptional = BddOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа статического анализа для SonarQube")
  @JsonProperty("sonarqube")
  SonarQubeOptional sonarQubeOptional = SonarQubeOptional.EMPTY

  @JsonIgnoreProperties("Настройки этапа сборки CF на поставке")
  @JsonProperty("build")
  BuildOptional buildOptional = BuildOptional.EMPTY
}
