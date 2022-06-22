/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
/**
 * Получить строку авторизации информационной базы
 * @return строка авторизации
 */
String getAuthString() {
  def credential = "--db-user ${DBUSERNAME}"
  try {
    credential = credential + " --db-pwd ${DBPASSWORD} "
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
  login = "${DBUSERNAME}"
  pass = ""
  try {
    pass = "${DBPASSWORD}"
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
  return !auth.isEmpty()
}
