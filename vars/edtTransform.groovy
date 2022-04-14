/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.EdtTransformOptional
import org.silverbulleters.usher.wrapper.Ring

@Field
PipelineConfiguration config

@Field
EdtTransformOptional optionals

/**
 * Трансформировать исходники EDT формата в XML
 * @param config конфигурацию
 */
void call(PipelineConfiguration config, EdtTransformOptional optionals) {
  this.config = config
  this.optionals = optionals

  def workspace = pwd()
  removeDir(optionals.outPath)
  removeDir(optionals.workspacePath)

  def command = Ring.workspaceExport(workspace, optionals)
  cmdRun(command)

  if (!fileExists(optionals.outPath)) {
    throw new Exception("Трансформация прошла неуспешно. Результирующий каталог с XML не существует")
  }

}