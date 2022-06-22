# Конфигурационный файл

В редакторах можно использовать JSON-схему (путь к схеме указывается через свойство `$scheme`.
Она расположена в каталоге `resources/schema.json`.

Снимок всех настроек по умолчанию в файле `resources/example.json`.

Все настройки конвейеров Usher2 делаются через конфигурационные файлы. Например, для запуска конвейера
`gitsync` может использоваться такая конфигурация:

`./tools/pipeline/gitsync.json`

```json
{
  "$scheme": "file:./resources/schema.json",
  "agent": "gitsync",
  "v8Version": "8.3.18",
  "stages": {
    "gitsync": true
  },
  "gitsync": {
    "useTemporaryInfobase": true,
    "tempPath": "./out/tmp"
  }
}
```

Пример конфигурационного файла для запуска конвейера `ci`:

`./tools/pipeline/ci.json`

```json
{
  "$scheme": "file:./resources/schema.json",
  "agent": "ci",
  "v8Version": "8.3.18",
  "debug": false,
  "notification": {
    "mode": "TELEGRAM",
    "telegram": {
       "chatId": "Telegram_Chat_ID" 
      }
  },
  "defaultInfobase": {
    "connectionString": "/FC:/template/demo",
    "auth": "admin1c"
  },
    "stages": {
    "prepareBase": false,
    "runExternal": false,
    "checkExtensions": false,
    "syntaxCheck": false,
    "smoke": false,
    "tdd": false,
    "bdd": false,
    "sonarqube": false,
    "build": false
  },
  "prepareBase": {
    "template": "./build/ext/1Cv8.dt",
    "repo": {
      "path": "C:/tmp/Storage",
      "auth": "cibot"
    }
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
    "checkExtensions": false,
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
    "agent": "sonar",
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

## Описание конфигурационных файлов

`debug` - режим включения отладочных логов конвейера. По умолчанию `false`.

`agent` - Имя/метки агента для запуска этапа. Например, `sonar-scanner`. Используется для всех этапов,
кроме `sonarqube`. По умолчанию `any`.

`v8Version` - версия платформы 1С. Например, `8.3.20.1549. По умолчанию`8.3`.

`notification` - настройка отправки уведомлений.

* `mode` - режим отправки уведомлений. Доступные значения: `NO_USE`, `EMAIL` , `SLACK` , `TELEGRAM`.
По умолчанию `NO_USE` (не использовать уведомления).

* `email` - почтовый ящик для уведомлений по email (несколько значений указывать через запятую).
По умолчанию `test@localhost`.

* `slack` - настройка уведомлений в Slack.

  * `channelName` - идентификатор канала уведомлений. По умолчанию `#build`.

* `telegram` - настройка уведомления в Telegram.
Используется плагин Jenkins [TelegramBot](https://github.com/jenkinsci/telegram-notifications-plugin)
  * `chatID` - идентификатор канала уведомлений.

`timeout` - общий таймаут на время работы конвейера

`defaultInfobase` - используемая информационная база по умолчанию

* `connectionString` - Строка подключения к информационной базе, например, `/F/my-path/to/ib`.
По умолчанию `/F.build/ib`.

* `auth` - идентификатор секрета Jenkins для авторизации в информационной базе (пользователь с паролем).
По умолчанию пустое значение.

`stages` - настройка использования этапов конвейера

Все значения опций по умолчанию - `false`. Для включения нужно перевести в значение `true`.

* `gitsync` - выгрузить историю хранилища 1С с помощью утилиты `gitsync`.

* `edtTransform` - трансформировать edt-формат конфигурации в xml.

* `prepareBase` - подготовить информационную базу.

* `runExternal` - выполнить произвольные внешние обработки с передачей параметров запуска

* `checkExtensions` - выполнить проверку применимости расширений

* `syntaxCheck` - проверить конфигурацию с помощью синтакс-проверки.

* `smoke` - запустить дымовое тестирование.

* `tdd` - запустить TDD.

* `bdd` - запустить BDD.

* `sonarqube` - запустить статический анализ.

* `build` - собрать cf на поставке с помощью `packman`.

* `yard` - запустить загрузку и обработку релизов конфигураций 1С.

`gitsync` - настройки этапа выгрузки истории хранилища 1С с помощью утилиты `gitsync`.

* `configPath` - путь к конфигурационному файлу `gitsync` для команды `all`. По умолчанию `./tools/JSON/gitsync_conf.JSON`.

* `auth` - идентификатор секрета Jenkins для авторизации в хранилище конфигурации. По умолчанию пустое значение.

* `useTemporaryInfobase` - признак использования временной информационной базы. База будет создана во временном
  каталоге `gitsync`. По умолчанию `false`.

* `tempPath` - путь к каталогу временных файлов, используемый gitsync при синхронизации. По умолчанию пустое значение.

`edtTransform` - настройка этапа трансформации edt-формата конфигурации в xml.

* `timeout` - таймаут выполнения этапа. По умолчанию `30`.

* `edt` - Модуль edt для утилиты ring. По умолчанию `edt`. Для использования, например, версии 2021.2
нужно указать `edt@2021.2.0`.

* `workspacePath` - каталог рабочей области проекта. По умолчанию `./build/workspace`.

* `sourcePath` - каталог edt-выгрузки конфигурации. По умолчанию `./src/cf`.

* `outPath` - каталог xml-выгрузки конфигурации. По умолчанию `./build/out`.

`preparebase` - настройки этапа подготовки информационной базы

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `sourcePath` - каталог с исходниками в формате xml. По умолчанию `./src/cf`.

* `template` - путь к шаблону базы в формате `*.dt`. По умолчанию пустое значение.

* `repo` - настройки подключения к хранилищу конфигурации 1С.

  * `path` - Путь к хранилищу конфигурации. Например, `tcp://repo-server/repo`. По умолчанию пустое значение.
  
  * `auth` - идентификатор секрета Jenkins для авторизации в хранилище конфигурации. По умолчанию пустое значение.
  
* `extensions` - Список расширений конфигурации 1С. Используется для обновления базы. По умолчанию пустой список.

    Например:

    ```json
    {
      "preparebase": {
         "extensions": [
            {
              "name": "МоеРасширение",
              "sourcePath": "./src/cfe/МоеРасширение"
            }
         ]
      }
    }
    ```

    В списке могут содержаться объекты со свойствами:

  * `name` - имя расширения конфигурации. Например, `МоеРасширениеКонфигурации`. По умолчанию пустое значение и будет
пропущено при обновлении информационной базы.

  * `sourcePath` - путь к исходному коду расширения 1С. По умолчанию пустое значение и будет пропущено
при обновлении информационной базы.

`runExtrnal` - настройки этапа выполнения внешних обработок 1с

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `pathEpf` - путь к каталогу с внешними обработками, по умолчанию `./tools/epf/`

* `vrunnerAdditionals` - список обработок с параметрами запуска
  * `vRunnerExecute` - Имя файла внешней обработки (МояВнешнаяОбработка.epf)
  * `vRunnerCommand` - Строка передаваемая в `ПараметрыЗапуска` ("ПараметрЗапускаМоейВнешнейОбработки1;ПараметрЗапускаМоейВнешнейОбработки2;")
  
  Например:

  ```json
    "runExternal": {
      "timeout": 100,
      "pathEpf": "./tools/epf/",
      "vrunnerAdditionals" : [
        {
          "vRunnerExecute": "Обработка1.epf",
          "vRunnerCommand": "ПараметрЗапуска1ДляОбработки1;ПараметрЗапуска2ДляОбработки1;"
        },
        {
          "vRunnerExecute": "Обработка2.epf",
          "vRunnerCommand": ""
        },
        {
          "vRunnerExecute": "Обработка3.epf",
          "vRunnerCommand": "ПараметрЗапуска1ДляОбработки3;"
        }
      ]
    },
  ```

* `settings` - путь к файлу настроек фреймворка тестирования, по умолчанию `./tools/JSON/vRunnerExternalOptions.JSON`
* `allurePath` -  путь к каталогу выгрузки отчета в формате Allure, по умолчанию `./out/runExternal/allure`
* `junitPath` - путь к файлу выгрузки отчета в формате jUnit, по умолчанию `./out/junit/runExternal.xml`

`checkExtensions` - настройки этапа проверки применимости расширений

* `timeout` - таймаут выполнения этапа. По умолчанию `40`.

* `repo` - настройки подключения к хранилищу конфигурации 1С.

  * `path` - Путь к хранилищу конфигурации. Например, `tcp://repo-server/repo`. По умолчанию пустое значение.
  
  * `auth` - идентификатор секрета Jenkins для авторизации в хранилище конфигурации. По умолчанию пустое значение.

* `extension` - имя расширения (_Выполнить проверку для указанного расширения с учетом всех ранее загружаемых расширений. Если имя расширения не указано, то проверяются все расширения в порядке загрузки._)

`syntaxCheck` - настройки этапа синтакс-проверки конфигурации 1С.

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `allurePath` - путь к каталогу выгрузки отчета в формате Allure

* `junitPath` - путь к файлу выгрузки отчета в формате jUnit

* `checkExtensions` - проверять все расширения в информационной базе. По умолчанию `false`.

* `groupByMetadata` - группировать результат проверки по метаданным. По умолчанию `true`.

* `mode` - список параметров проверки. По умолчанию:

    ```json
    [
      "-ThinClient",
      "-Server",
      "-ConfigLogIntegrity",
      "-HandlersExistence",
      "-ExtendedModulesCheck"
    ]
    ```

* `exceptionFile` - путь к файлу с исключениями. Если файл не существует, то анализ будет произведен без его учета.
По умолчанию `./tools/syntax-check/exceptionFile.txt`.

`smoke` - настройки этапа дымового тестирования.

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `xddConfig` - путь к конфигурационному файлу xUnitFor1c. По умолчанию `./tools/JSON/smokeTestRunnerConf.json`.

* `testPath` - путь к каталогу / к файлу с тестами, или название подсистемы. По умолчанию `$addroot/tests/smoke`.

* `allurePath` - путь к каталогу выгрузки отчета в формате Allure

* `junitPath` - путь к файлу выгрузки отчета в формате jUnit

* `pathXUnit` - путь к внешней обработке xddTestRunner.epf, по умолчанию в пакете vanessa-add

`tdd` - настройки этапа TDD (Test-driven development)

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `xddConfig` - путь к конфигурационному файлу xUnitFor1c. По умолчанию `./tools/JSON/xddTestRunnerConf.json`.

* `testPath` - путь к каталогу / к файлу с тестами, или название подсистемы. По умолчанию `./tests/unit`.

* `allurePath` - путь к каталогу выгрузки отчета в формате Allure

* `junitPath` - путь к файлу выгрузки отчета в формате jUnit

* `pathXUnit` - путь к внешней обработке xddTestRunner.epf, по умолчанию в пакете vanessa-add

`bdd` - настройки этапа BDD (Behavior-driven development)

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `allurePath` - путь к каталогу выгрузки отчета в формате Allure

* `pathVanessa` - путь к внешней обработке bddRunner.epf, по умолчанию в пакете vanessa-add

* `vanessasettings` - путь к файлу настроек фреймворка тестирования, по умолчанию `./tools/JSON/vanessaConf.json`

`sonarqube` - настройки этапа статического анализа для SonarQube.

* `agent` - имя/метки агента для запуска этапа. Например, `sonar-scanner`. По умолчанию `any`.

* `toolId` - идентификатор утилиты sonar-scanner (глобальные инструменты Jenkins). По умолчанию `sonar-scanner`.

* `serverId`- идентификатор настроек подключения к серверу SonarQube. По умолчания `SonarQube`.

* `debug` - режим отладки sonar-scanner. По умолчанию `false`.

* `useBranch` - использовать ветки при анализе. По умолчанию `false`.

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

`build` - настройки этапа сборки CF на поставке.

* `timeout` - таймаут выполнения этапа. По умолчанию `100`.

* `distPath` - путь к собранной cf на поставке. По умолчанию `.packman/1cv8.cf`.

* `errorIfJobStatusOfFailure` - прерывать этап если статус сборки `FAILURE`. По умолчанию `false`.

`yard` - настройки этапа синхронизации релиза 1С в git-проектом.

* `timeout` - таймаут выполнения этапа. По умолчанию `300`.

* `appName` - имя конфигурации с сайта releases.1c.ru. Например, `EnterpriseERP20`. По умолчанию пустое значение.

* `descriptionPath` - путь к файлу описания конфигурации. По умолчанию `./description.json`.

* `yardSettingsPath` - путь к файлу настроек yard. По умолчанию `./yardsettings.json`.

* `auth` - идентификатор секрета для авторизации на сайте releases.1c.ru. По умолчанию пустое значение.

* `workspacePath` - каталог запуска yard. По умолчанию `./`.

* `branch` - имя ветки для git push. По умолчанию `master`.

* `debug` - режим включения отладочных логов yard. По умолчанию `debug`.
