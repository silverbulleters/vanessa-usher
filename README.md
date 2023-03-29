<img src="resources/images/logo_270x270.png" alt="drawing" width="50"/> <font size="8"> [Курс "Имплементация инженерных практик" по ссылке](https://silverbulleters.org/implementacia) </font>

# Vanessa-Usher: библиотека автоматизации сборочного цикла для 1С:Предприятие

Библиотека предназначена для быстрого создания сборочных линий CI/CD для прикладных решений на платформе 1С:Предприятие.

Возможности:
* Экспорт истории хранилища 1С в Git
* Проверочный контур, состоящий из шагов:
  * Подготовка окружения
  * Синтаксическая проверка платформой 1С
  * Статический анализ проекта в SonarQube
  * Дымовое тестирование
  * Модульное тестирование
  * Поведенческое тестирование
  * Публикация отчетов `Allure` и `jUnit`
  * Сборка поставки конфигурации
  * Отправка уведомлений
    * На электронную почту
    * В Slack

## Состав библиотеки
* Каталог `vars` - основной каталог с методами библиотеки.
* Каталог `examples/template` - шаблон нового проекта.

## Быстрый старт

### Подключение

Подключить библиотеку по инструкции [Using libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/#using-libraries).
В поле `Name` установить значение `usher2`.

### Jekinsfiles

Рассмотрим примеры из шаблона проекта `examples/template`.

Запуск экспорта истории хранилища 1С (`Jenkinsfile_gitsync`):

```groovy
@Library('usher2') _

repoSyncPipeline('tools/pipeline/gitsync.json')
```

![image-20210810132241700](/resources/images/pipe_gitsync.png)

Запуск проверочного контура (`Jenkinsfile`):

```groovy
@Library('usher2') _

buildPipeline('tools/pipeline/ci.json')
```

![image-20210810132219338](/resources/images/pipe_ci.png)

### Настройка конфигурации

Для каждого pipeline нужно создать конфигурационный файл. 

Пример запуска "Экспорт истории хранилища 1С":
```json
{
  "$schema": "https://gitlab.silverbulleters.org/products/avtoinfrastruktura/vanessa-usher2/-/raw/develop/resources/schema.json",
  "agent": "gitsync",
  "v8Version": "8.3",
  "notification": {
    "mode": "EMAIL",
    "email": "test@mail.ru"
  },
  "defaultInfobase": {
    "connectionString": "/F./build/ib",
    "auth": ""
  },
  "stages": {
    "gitsync": true
  }
}
```

Пример запуска проверочного контура:
```json
{
  "agent": "slave",
  "v8Version": "8.3.15",
  "notification": {
    "mode": "EMAIL",
    "email": "test@mail.ru"
  },
  "defaultInfobase": {
    "connectionString": "/F./build/ib"
  },
  "stages": {
    "prepareBase": true,
    "syntaxCheck": true,
    "sonarqube": true,
    "smoke": true,
    "tdd": true,
    "bdd": true,
    "build": true
  },
  "prepareBase": {
    "sourcePath": "./src/cf"
  },
  "sonarqube": {
    "agent": "sonarqube",
    "toolId": "sonar-scanner",
    "serverId": "SonarQube",
    "debug": false
  }
}
```

Параметры по умолчанию можно изучить здесь [example.json](resources/example.json).

## Благодарности участникам разработки
[@otymko](https://github.com/otymko)
[@M0okRu](https://github.com/M0okRu) 

## Лицензия

Лицензию читать здесь [LICENSE.md](LICENSE.md)
