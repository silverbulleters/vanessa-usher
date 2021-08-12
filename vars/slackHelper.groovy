import org.silverbulleters.usher.NotificationInfo

void sendNotification(String channelId, NotificationInfo info) {
  blocks = getSuccessBlock(info)
  slackSend(channel: channelId, blocks: blocks)
}

void sendErrorNotification(String channelId, NotificationInfo info) {
  blocks = [
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
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "*Последний коммит:*\n ${info.commitId}: ${info.commitMessage}"
          ]
      ],
      [
          "type": "section",
          "text": [
              "type": "mrkdwn",
              "text": "*Лог выполнения:*\n ${info.buildUrl}consoleText"
          ]
      ]
  ]
  slackSend(channel: channelId, blocks: blocks)
}

private getSuccessBlock(NotificationInfo info) {
  section3 = [
      "type"  : "section",
      "fields": [
          [
              "type": "mrkdwn",
              "text": "*Ветка:*\n ${info.branchName}"
          ]
      ]
  ]

  if (info.showTestResults) {
    testResults = [
        "type": "mrkdwn",
        "text": "*Результат тестирования:*\n Успешно: ${info.successCount}, Упало: ${info.failedCount}, Пропущено: ${info.skippedCount}"
    ]
    section3.fields.add(testResults)
  }

  blocks = [
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
      section3,
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