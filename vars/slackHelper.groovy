import org.silverbulleters.usher.NotificationInfo

void sendNotification(NotificationInfo info) {

  def block = getSuccessBlock(info)

  slackSend(channel: info.channelId, blocks: blocks)
}

private def getSuccessBlock(NotificationInfo info) {
  def blocks = [
      [
          "type": "divider"
      ],
      [
          "type": "header",
          "text": [
              "type" : "plain_text",
              "text" : "${common.getEmojiStatusForSlack(info.status)} Проект \'${info.projectName}\', сборка #${info.buildNumber}",
              "emoji": true
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "${info.buildUrl}"
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "*Статус:*\n ${info.status}"
          ]
      ],
      [
          "type"  : "section",
          "fields": [
              [
                  "type": "mrkdwn",
                  "text": "*Ветка:*\n ${info.branchName}"
              ],
              [
                  "type": "mrkdwn",
                  "text": "*Результат тестирования:*\n Успешно: ${info.successCount}, Упало: ${info.failedCount}, Пропущено: ${info.skippedCount}"
              ]
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "*Последний коммит:*\n ${info.commitId}: ${info.commitMessage}"
          ]
      ]
  ]
  return blocks
}

private def getErrorBlock(NotificationInfo info) {
  def blocks = [
      [
          "type": "divider"
      ],
      [
          "type": "header",
          "text": [
              "type" : "plain_text",
              "text" : "${common.getEmojiStatusForSlack(info.status)} Проект \'${info.projectName}\', сборка #${info.buildNumber}",
              "emoji": true
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "${info.buildUrl}"
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "*Статус:*\n ${info.status}"
          ]
      ],
      [
          "type"  : "section",
          "fields": [
              [
                  "type": "mrkdwn",
                  "text": "*Ветка:*\n ${info.branchName}"
              ],
              [
                  "type": "mrkdwn",
                  "text": "*Результат тестирования:*\n Успешно: ${info.successCount}, Упало: ${info.failedCount}, Пропущено: ${info.skippedCount}"
              ]
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "*Последний коммит:*\n ${info.commitId}: ${info.commitMessage}"
          ]
      ]
  ]
  return blocks
}