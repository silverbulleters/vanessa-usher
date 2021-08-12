import org.silverbulleters.usher.NotificationInfo

void sendNotification(String email, NotificationInfo info) {
  message = ""
  if (info.status == 'SUCCESS') {
    message = getSuccessMessage(info)
  } else {
    message = getErrorMessage(info)
  }

  subject = "${common.getEmojiStatusForEmail(info.status)} Проект \'${info.projectName}\', сборка #${info.buildNumber}"

  emailext(
      mimeType: 'text/html',
      body: message,
      subject: subject,
      to: email
  )
}

void sendErrorNotification(String email, NotificationInfo info) {
  // FIXME: не реализовано
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

private static String getErrorMessage(NotificationInfo info) {
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