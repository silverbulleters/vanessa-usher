/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.NotificationInfo
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.NotificationMode

/**
 * Отправить уведомление по почте / в slack
 * @param config конфигурация
 * @param info информация о уведомлении
 */
void call(PipelineConfiguration config, NotificationInfo info) {

  def address
  def providerNotification
  if (config.notification.mode == NotificationMode.SLACK) {
    providerNotification = slackHelper
    address = config.notification.slack.channelName
  } else if (config.notification.mode == NotificationMode.EMAIL) {
    providerNotification = emailHelper
    address = config.notification.email
  } else if (config.notification.mode == NotificationMode.TELEGRAM) {
    providerNotification = telegramHelper
    addres = config.notification.telegram.chatId
  } else {
    return
  }

  if (config.stages.gitsync) {
    providerNotification.sendErrorNotification(address, info)
  } else {
    providerNotification.sendNotification(address, info)
  }

}
