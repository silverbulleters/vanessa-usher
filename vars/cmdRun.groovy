/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
/**
 * Функция исполнения команд. Установка локализации при использовании MS Windows
 * Пример вызова: cmdRun.Выполнить("dir /w")
 */
def call(String command) {
  if (isUnix()) {
    sh "${command}"
  } else {
    def logCmd = ""
    bat " chcp 65001\n${logCmd}${command}"
  }
}
