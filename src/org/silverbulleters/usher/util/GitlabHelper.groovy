/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.util

/**
 * Хелпер для взаимодействия с GitLab
 */
class GitlabHelper {

  /**
   * Проверить url ссылку на принадлежность к GitLab
   * @param gitUrl - произвольная ссылка
   * @return
   */
  static boolean isUrlGitlab(String gitUrl) {
    def (String host) = (gitUrl =~ /((?:https|http):\/\/.+?\/)(.+).git/)[0][1..2]
    def url = "${host}/api/v4/projects"
    def baseUrl = new URL(url)
    def connection = (HttpURLConnection) baseUrl.openConnection()
    def code = connection.getResponseCode()
    return code == 200
  }

  /**
   * Получить контент из проекта gitlab
   * @param gitUrl ссылка на проект gitlab
   * @param token токен авторизации
   * @param branch имя ветки
   * @param path путь к файлу
   * @return текстовое содержимое
   */
  static String getContent(String gitUrl, String token, String branch, String path) {
    def (String host, String projectPath) = (gitUrl =~ /((?:https|http):\/\/.+?\/)(.+).git/)[0][1..2]
    def url = "${host}/api/v4/projects/${encodeUrl(projectPath)}/repository/files/${encodeUrl(path)}/raw?ref=${branch}"
    return getHttpResult(url, token)
  }

  /**
   * Закодировать значение в юникод
   * @param value произвольное текстовое значение
   * @return
   */
  static def encodeUrl(String value) {
    return URLEncoder.encode(value, "UTF-8");
  }

  private static def getHttpResult(String url, String token) {
    def baseUrl = new URL(url)
    def connection = (HttpURLConnection) baseUrl.openConnection()
    connection.setRequestProperty("PRIVATE-TOKEN", token)
    return connection.getInputStream().text
  }

}