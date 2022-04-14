# Конвейер `Выгрузка истории хранилища 1С в git-репозиторий`

Конвейер поможет автоматизировать выгрузку истории хранилища 1С в git-репозиторий. Внутри используется приложение
gitsync.

Пример конфигурационного файла:

```json
{
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

### Git Credential Manager

При первом запуске pipeline gitsync, если используется `credential.helper=manager.core`, пара логин\пароль будет автоматически добавлена в credetial, что позволяет отказаться от ручного ввода логина\пароля на ноде Jenkins-агента.

При установке git for windows предалагается по умолчанию установить credential helper

![image-20220201095111619](../images/image-20220201095111619.png)

Git config после установки Git Credential Manager Core.

```conf
>>git config --sytsem --list | findstr credential
credential.helper=manager.core
credential.https://dev.azure.com.usehttppath=true

>>git config --global --list | findstr credential
credential.http://your-gitlab-server.com.provider=generic
```

#### Отключение credential.helper

В случае, если нужно настроить доступ к внешнему репозиторию с одной ноды под разными пользователями, есть возможность отключить использование credential manager.

[Поддержка авторизации для двух логинов по одному Uri не реализована.](https://github.com/Microsoft/Git-Credential-Manager-for-Windows/issues/363)

```conf
git config --system --unset-all credential.helper
git config --global --unset-all credential.helper
git config --unset-all credential.helper
```
