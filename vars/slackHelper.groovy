/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.NotificationInfo

/**
 * Отправить уведомления в slack
 * @param channelId идентификатор канала в slack
 * @param info информация об уведомлении
 */
void sendNotification(String channelId, NotificationInfo info) {
  blocks = getSuccessBlock(info)
  slackSend(channel: channelId, blocks: blocks)
}

/**
 * Отправить уведомление с ошибкой
 * @param channelId идентификатор канала в slack
 * @param info информация об уведомлении
 */
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