# Конвейер `Проверочный контур проекта 1С`

Этот конвейер поможет построить проверочный контур для проекта 1С. 

В его состав входят следующие шаги:

* Трансформировать исходный код из формата EDT в XML
* Подготовить информационную базу 1С
* Выполнить произвольные внешние обработки с передачей параметрв
* Выполнить проверку применимости расширений
* Провести синтаксическую проверку конфигурации и расширений 1С
* Провести статический анализ проекта 1С в SonarQube
* Провести дымовое тестирование, используя ADD
* Провести модульное тестирование, используя xUnit
* Провести поведенческое тестирование
* Собрать и опубликовать в артефактах файл поставки 1С
* Опубликовать в Jenkins накопленные allure и xunit отчеты

Пример конфигурационного файла `ci.json`:
```json
{
  "agent": "windows && 1s",
  "v8Version": "8.3.18",
  "defaultInfobase": {
    "connectionString": "/Smy-server/infobase",
    "auth": "CI_MY_AUTH_TO_IB"
  },
  "stages": {
    "prepareBase": true,
    "runExternal": true,
    "checkExtensions": true,
    "syntaxCheck": true,
    "smoke": true,
    "tdd": true,
    "bdd": true,
    "sonarqube": true,
    "build": true
  },
  "prepareBase": {
    "template": "./build/ext/1Cv8.dt",
    "repo": {
      "path": "C:/tmp/Storage_SSL31",
      "auth": "cibot"
    },
    "extensions": [
      {
        "name":"extension1",
        "sourcePath": "./src/cfe/extension1"
      },
      {
        "name": "extension2",
        "sourcePath": "./src/cfe/extension2"
      }
    ]
  },
  "runExternal": {
    "timeout": 20,
    "pathEpf": "./tools/epf/",
    "vRunnerExecute": [
      "epf1.epf",
      "epf2.epf",
      "epf3.epf",
      "epf4.epf"
    ],
    "vRunnerCommand": [
      "Param1;Param2",
      "",
      "Param3"
    ]
  },
  "checkExtensions": {
    "repo": {
      "path": "C:/tmp/Storage_SSL31",
      "auth": "cibot"
    }
  },
  "syntaxCheck": {
    "timeout": 100,
    "allurePath": "./out/syntaxCheck/allure",
    "junitPath": "./out/junit/syntaxCheck.xml",
    "checkExtensions": true,
    "groupByMetadata": true,
    "mode": [
      "-ThinClient",
      "-Server",
      "-ConfigLogIntegrity",
      "-HandlersExistence",
      "-ExtendedModulesCheck"
    ],
    "exceptionFile": "./tools/syntax-check/exceptionFile.txt"
  },
  "smoke": {
    "timeout": 100,
    "testPath": "./tests/smoke/Тесты_ОткрытиеФормКонфигурации.epf"
  },
  "tdd": {
    "timeout": 10
  },
  "bdd": {
    "timeout": 100,
    "vanessasettings": "./tools/vanessaConf.json"
  },
  "sonarqube": {
    "timeout": 100,
    "agent": "any",
    "toolId": "sonar-scanner",
    "serverId": "SonarQube",
    "debug": false,
    "useBranch": false
  },
  "build": {
    "timeout": 100,
    "distPath": ".packman/1cv8.cf",
    "errorIfJobStatusOfFailure": false
  }
}
```

## prepareBase

Доступны три варианта подготовки базы.
* Обновление из шаблона *.dt файла
* Обновление из хранилища
* Обновление из исходников

Для работы с расширениями в этом шаге:
* Для загрузки расщирений в конфигурацию, расширения должны быть отключены от хранилища.
* 