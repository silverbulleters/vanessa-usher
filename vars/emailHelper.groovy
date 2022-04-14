/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.NotificationInfo

/**
 * Отправить уведомление по email
 * @param email электронная почта
 * @param info информация об уведомлении
 */
void sendNotification(String email, NotificationInfo info) {
  message = ""
  if (info.status == 'SUCCESS') {
    message = getSuccessMessage(info)
  } else {
    message = getFailedMessage(info)
  }

  emailext(
      mimeType: 'text/html',
      body: message,
      subject: getSubject(info),
      to: email
  )
}

/**
 * Отправить уведомление по email с ошибкой
 * @param email электронная почта
 * @param info информация об уведомлении
 */
void sendErrorNotification(String email, NotificationInfo info) {
  message = getErrorMessage(info)

  emailext(
      mimeType: 'text/html',
      body: message,
      subject: getSubject(info),
      to: email
  )
}

private String getSubject(NotificationInfo info) {
  return "${common.getEmojiStatusForEmail(info.status)} Проект \'${info.projectName}\', сборка #${info.buildNumber}"
}

private static String getSuccessMessage(NotificationInfo info) {
  def message = """
  <html>
    <body>
        <b>Ссылка на сборку:</b>
        <br />
        ${info.buildUrl}
        <br />
        <b>Статус:</b>
        <br />
        ${info.status}
        <br />
        <b>Ветка</b>:
        <br />
        ${info.branchName}
        <br />
        %TEST_RESULTS%
        <b>Последний коммит</b>:
        <br />
        ${info.commitId}
    </body>
  </html>
  """

  message = replaceTestResults(info, message)

  return message
}

private static String getFailedMessage(NotificationInfo info) {
  String message = """
  <html>
    <body>
      <b>Ссылка на сборку:</b>
      <br />
      ${info.buildUrl}
      <br />
      <b>Статус:</b>
      <br />
      ${info.status}
      <br />
      <b>Ветка</b>:
      <br />
      ${info.branchName}
      <br />
      %TEST_RESULTS%
      <b>Последний коммит</b>:
      <br />
      ${info.commitId}
    </body>
  </html>
  """

  message = replaceTestResults(info, message)

  return message
}

private static String getErrorMessage(NotificationInfo info) {
  String message = """
  <html>
    <body>
      <b>Ссылка на сборку:</b>
      <br />
      ${info.buildUrl}
      <br />
      <b>Статус:</b>
      <br />
      ${info.status}
      <br />
      <b>Ветка</b>:
      <br />
      ${info.branchName}
      <br />
      %TEST_RESULTS%
      <b>Лог выполнения</b>:
      <br />
      ${info.buildUrl}consoleText
    </body>
  </html>
  """

  message = replaceTestResults(info, message)

  return message
}

private static String replaceTestResults(NotificationInfo info, String message) {
  if (info.showTestResults) {
    def testResults = """
        <b>Результат тестирования:</b>
        <br />
        Успешно: ${info.successCount}, Упало: ${info.failedCount}, Пропущено: ${info.skippedCount}
        <br />
    """
    message = message.replace('%TEST_RESULTS%', testResults)
  } else {
    message = message.replace('%TEST_RESULTS%', '')
  }
  return message
}