/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyDescription
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.additional.MatrixTesting
import org.silverbulleters.usher.config.additional.NotificationOptional
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.config.stage.BuildOptional
import org.silverbulleters.usher.config.stage.CheckExtensionsOptional
import org.silverbulleters.usher.config.stage.EdtTransformOptional
import org.silverbulleters.usher.config.stage.GitsyncOptional
import org.silverbulleters.usher.config.stage.PrepareBaseOptional
import org.silverbulleters.usher.config.stage.RunExternalDataProcessorsOptional
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.config.stage.SonarQubeOptional
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.config.stage.TddOptional
import org.silverbulleters.usher.config.stage.YardOptional

/**
 * Настройки pipeline
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class PipelineConfiguration implements Serializable {
  @JsonPropertyDescription("Версия платформы 1С. Например, `8.3.20.1549`")
  String v8Version = "8.3"

  @JsonPropertyDescription("""Имя/метки агента для запуска этапа. Например, `sonar-scanner`. 
  Используется для всех этапов, кроме `sonarqube`.
  """)
  String agent = "any"

  @JsonPropertyDescription("Настройка для мульти-тестирования на разных ос / версиях 1С")
  MatrixTesting matrixTesting = []

  @JsonPropertyDescription("Режим включения отладочных логов конвейера")
  boolean debug = false

  @JsonPropertyDescription("Настройка отправки уведомлений")
  NotificationOptional notification = new NotificationOptional()

  @JsonPropertyDescription("Путь к конфигурационному файлу vanessa-runner")
  String vrunnerConfig = "./tools/JSON/vRunner.json"

  @JsonPropertyDescription("Путь к каталогу с отчетами в в формате jUnit")
  String junitPath = UsherConstant.JUNIT_PATH

  @JsonPropertyDescription("Общий таймаут на время работы конвейера")
  int timeout = 100

  @JsonPropertyDescription("Информационная база по умолчанию")
  InfoBase defaultInfobase = new InfoBase()

  @JsonPropertyDescription("Настройка использования этапов")
  Stages stages = new Stages()

  @JsonPropertyDescription("Настройки этапа выгрузки истории хранилища 1С с помощью утилиты `gitsync`")
  @JsonProperty("gitsync")
  GitsyncOptional gitsyncOptional = new GitsyncOptional()

  @JsonPropertyDescription("Настройка этапа трансформации edt-формата конфигурации в xml")
  @JsonProperty("edtTransform")
  EdtTransformOptional edtTransformOptional = new EdtTransformOptional()

  @JsonPropertyDescription("Настройки этапа подготовки информационной базы")
  @JsonProperty("preparebase")
  PrepareBaseOptional prepareBaseOptional = new PrepareBaseOptional()

  @JsonPropertyDescription("Настройки этапап выполнения произвольных внешних обработок 1с")
  @JsonProperty("runExternal")
  RunExternalDataProcessorsOptional runExternal = new RunExternalDataProcessorsOptional()

  @JsonPropertyDescription("Настройки этапа применимости расширения для использования в конкретной информационной базе конфигурации 1С")
  @JsonProperty("checkExtensions")
  CheckExtensionsOptional checkExtensionsOptional = new CheckExtensionsOptional()

  @JsonPropertyDescription("Настройки этапа синтакс-проверки конфигурации 1С")
  @JsonProperty("syntaxCheck")
  SyntaxCheckOptional syntaxCheckOptional = new SyntaxCheckOptional()

  @JsonPropertyDescription("Настройки этапа дымового тестирования")
  @JsonProperty("smoke")
  SmokeOptional smokeOptional = new SmokeOptional()

  @JsonPropertyDescription("Настройки этапа TDD (Test-driven development)")
  @JsonProperty("tdd")
  TddOptional tddOptional = new TddOptional()

  @JsonPropertyDescription("Настройки этапа BDD (Behavior-driven development)")
  @JsonProperty("bdd")
  BddOptional bddOptional = new BddOptional()

  @JsonPropertyDescription("Настройки этапа статического анализа для SonarQube")
  @JsonProperty("sonarqube")
  SonarQubeOptional sonarQubeOptional = new SonarQubeOptional()

  @JsonPropertyDescription("Настройки этапа сборки CF на поставке")
  @JsonProperty("build")
  BuildOptional buildOptional = new BuildOptional()

  @JsonPropertyDescription("Настройки этапа загрузки и обработки релизов 1С")
  @JsonProperty("yard")
  YardOptional yardOptional = new YardOptional()

}
