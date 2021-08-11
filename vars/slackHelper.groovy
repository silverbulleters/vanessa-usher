import org.silverbulleters.usher.NotificationInfo

void sendNotification(NotificationInfo info) {

//  def info = new NotificationInfo()
//  info.channelId = "#demo-cicd"
//  info.icon = ":white_check_mark:" // ":x:"
//  info.projectName = "MyProject"
//  info.buildNumber = "1"
//  info.buildUrl = "https://ci.siverbulleters.org/myproject"
//  info.branchName = "fix/ci"
//  info.commitId = "123456"
//  // TODO: обрезать до 100 символов и добавить ...
//  info.commitMessage = "DEVOPS-01 Я что-то сделал и все пропало"

  blocks = [
      [
          "type": "divider"
      ],
      [
          "type": "header",
          "text": [
              "type" : "plain_text",
              "text" : "${info.icon()} Проект \"${info.projectName}\", сборка #${info.buildNumber}",
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

  slackSend(channel: info.channelId, blocks: blocks)
}