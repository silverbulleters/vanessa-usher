# Конвейер `Проверочный контур проекта 1С`

Этот конвейер поможет построить проверочный контур для проекта 1С.

В его состав входят следующие шаги:

* Трансформировать исходный код из формата EDT в XML
* Подготовить информационную базу 1С
* Выполнить произвольные внешние обработки с передачей параметров
* Провести проверку применимости расширений
* Провести синтаксическую проверку конфигурации и расширений 1С
* Провести статический анализ проекта 1С в SonarQube
* Провести дымовое тестирование, используя ADD
* Провести модульное тестирование, используя xUnit
* Провести поведенческое тестирование
* Собрать и опубликовать в артефактах файл поставки 1С
* Опубликовать в Jenkins накопленные allure и junit отчеты

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
    "template": "./build/template/1Cv8.dt",
    "repo": {
      "path": "C:/tmp/Storage",
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
    "vrunnerAdditionals" : [
      {
        "vRunnerExecute": "Обработка1.epf",
        "vRunnerCommand": "ПараметрЗапуска1ДляОбработки1;ПараметрЗапуска2ДляОбработки1;"
      }
    ]
  },
  "checkExtensions": {
    "repo": {
      "path": "C:/tmp/Storage",
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

Используется приложение [vrunner](https://github.com/vanessa-opensource/vanessa-runner)

Доступны три варианта подготовки базы.

* Обновление из шаблона *.dt файла
* Обновление из хранилища
* Обновление из исходников

Для работы с расширениями в этом шаге:

* Сборка расширения происходит из исходников, других вариантов не предусмотрено
* Для загрузки расщирений в конфигурацию, расширения должны быть отключены от хранилища.

## runExternal

Последовательное выполнение внешних обработок 1с, с возможностью передать параметры запуска в вызываемые обработки.

Используется приложение [vrunner](https://github.com/vanessa-opensource/vanessa-runner)

* Если параметры запуска передавать не нужно, в конфигурационном файле передаем пустую строку.

  _Пример:_

```json
  "vrunnerAdditionals" : [
    {
      "vRunnerExecute": "Обработка1.epf",
      "vRunnerCommand": ""
    }
  ]
```

## checkExtensions

Выполняет проверку применимости расширений.

Используется приложение [vrunner](https://github.com/vanessa-opensource/vanessa-runner)

* используется параметр пакетного запуска конфигуратора 1с `/CheckCanApplyConfigurationExtensions`

## syntaxCheck

Выполнение синтакс-проверки конфигурации.

Используется приложение [vrunner](https://github.com/vanessa-opensource/vanessa-runner)

* файл исключений для синтакс-проверки

_Пример:_

```txt
Соединение с хранилищем конфигурации не установлено
Неразрешимые ссылки на объекты метаданных
Обработка.ПанельАдминистрированияБСП.Форма.ИнтернетПоддержкаИСервисы.Справка
Имя не уникально
ОбщийМодуль.ПодключениеСервисовСопровождения.Модуль
```

* Возможность выполнить проверку всех подключенных расширений
  * `"-Allextensions": true`
* Включенные по умолчанию параметры синтакс-проверки:
  * `"-ThinClient"` - синтаксический контроль модулей для режима эмуляции среды управляемого приложения (тонкий клиент), выполняемого в файловом режиме;
  * `"-Server"` - синтаксический контроль модулей в режиме эмуляции среды сервера «1С:Предприятия»;
  * `"-ConfigLogIntegrity"` - проверка логической целостности конфигурации. Стандартная проверка, обычно выполняемая перед обновлением базы данных;
  * `"-HandlersExistence"` - проверка существования назначенных обработчиков. Проверка существования обработчиков событий интерфейсов, форм и элементов управления;
  * `"-ExtendedModulesCheck"` - проверка обращений к методам и свойствам объектов «через точку» (для ограниченного набора типов); проверка правильности строковых литералов – параметров некоторых функций, таких как ПолучитьФорму().

## smoke

Выполнить дымовые тесты.

Используется приложение [vrunner](https://github.com/vanessa-opensource/vanessa-runner)

* Пример конфигурационого файла [`./tools/JSON/smokeTestRunnerConf.json`](/examples/template/tools/JSON/smokeTestRunnerConf.json)
  * содержит настройки
  * содержит исключения для дымовых тестов

## tdd

Вызов модульных тестов.

Используется приложение [vanessa-add](https://github.com/oscript-library/add)

[Документация на сайте проекта](https://github.com/oscript-library/add/tree/master/doc/xdd)

## bdd

Вызов поведенческих тестов.

Используется приложение [vanessa-add](https://github.com/oscript-library/add)

[Документация на сайте проекта](https://github.com/oscript-library/add/tree/master/doc/bdd)

## sonarqube

Выполнить статический анализ кода.

Анализ выполняется в два этапа.

* sonar-scanner
* обработка анализа на сервере SonarQube

## build

Подготовить файл поставки.

Используется приложение [packman](https://github.com/oscript-library/packman)
