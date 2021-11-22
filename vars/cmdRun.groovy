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
void call(String command) {
  if (isUnix()) {
    // в linux необходимо экранировать символы $, например для `$runnerRoot`
    def newCommand = common.shieldSymbols(command)
    sh "${newCommand}"
  } else {
    toRun = [
        'chcp 65001',
        "${command}"
    ].join('\n')
    bat toRun
  }
}
