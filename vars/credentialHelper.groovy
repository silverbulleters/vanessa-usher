/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.UsherConstant

/**
 * Получить строку авторизации информационной базы
 * @return строка авторизации
 */
String getAuthString() {
  def credential = "--db-user ${USERNAME}"
  try {
    credential = credential + " --db-pwd ${PASSWORD} "
  } catch (ignore) {
  }
  return credential
}

/**
 * Получить строку авторизации хранилища 1С
 * @return строка авторизации
 */
String getAuthRepoString() {
  def credential = "--storage-user ${USERNAME}"
  try {
    credential = credential + " --storage-pwd ${PASSWORD} "
  } catch (ignore) {
  }
  return credential
}

/**
 * Получить свою строку авторизации
 * @param userName имя параметра пользователя
 * @param passName имя параметры пароля
 * @return строка авторизации
 */
String getCustomAuth(String userName, String passName) {
  def credential = "${userName} ${USERNAME}"
  try {
    credential = credential + " ${passName} ${PASSWORD} "
  } catch (ignore) {
  }
  return credential
}

/**
 * Получить строку авторизации для тест-клиента
 * @return строка авторизации
 */
String getTestClientWithAuth() {
  def baseValue = '%s:%s:1538'
  login = "${USERNAME}"
  pass = ""
  try {
    pass = "${PASSWORD}"
  } catch (ignored) {
  }
  def credentialTestClient = String.format(baseValue, login, pass)
  return credentialTestClient
}

/**
 * Проверить заполненность идентификатора с секретом
 * @param auth идентификатор секрета
 * @return
 */
boolean authIsPresent(String auth) {
  return !(auth == UsherConstant.EMPTY_VALUE || auth.isEmpty())
}

/**
 * Проверить существование секрета
 * @param id идентификатор секрета
 * @return признак существования секрета
 */
boolean exist(String id) {
  boolean result = false
  try {
    withCredentials([usernamePassword(credentialsId: id, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      result = true;
    }
  } catch (e) {
    println(e.getMessage())
    result = false
  }
  logger.debug("Credential " + id + "  exist? " + result)
  return result
}