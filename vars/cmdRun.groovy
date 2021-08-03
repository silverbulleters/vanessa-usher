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
