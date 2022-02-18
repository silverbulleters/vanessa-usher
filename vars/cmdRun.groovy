/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
/**
 * Выполнить произвольную команду
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
