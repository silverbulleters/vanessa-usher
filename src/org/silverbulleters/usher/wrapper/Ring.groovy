/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.wrapper

import org.silverbulleters.usher.config.stage.EdtTransformOptional

/**
 * Помощник формирования команд Ring
 */
class Ring {

  /**
   * Конвертировать проект из файлового представления 1C:EDT в xml-выгрузку конфигурации
   * @param workspace рабочий каталог проекта
   * @param optional настойки edtTransform
   * @return строка команды
   */
  static String workspaceExport(String workspace, EdtTransformOptional optional) {
    def workspacePath = getAbsolutePath(workspace, optional.workspacePath)
    def outPath = getAbsolutePath(workspace, optional.outPath)
    def sourcePath = getAbsolutePath(workspace, optional.sourcePath)

    def command = [
        "ring",
        optional.edt,
        "workspace",
        "export",
        "--workspace-location", workspacePath,
        "--configuration-files", outPath,
        "--project", sourcePath
    ]
    return command.join(" ")
  }

  /**
   * Получить абсолютный путь
   * @param workspace рабочий каталог
   * @param path относительный путь
   * @return абсолютный путь
   */
  static private String getAbsolutePath(String workspace, String path) {
    return new File(workspace, path).toString()
  }

}
