/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * Настройки уведомлений
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class NotificationOptional {
  static final NotificationOptional EMPTY = new NotificationOptional()

  @JsonPropertyDescription("Режим уведомлений")
  NotificationMode mode = NotificationMode.NO_USE

  @JsonPropertyDescription("Почтовый ящик для уведомлений по email (несколько значений указывать через запятую).")
  String email = "test@localhost"

  @JsonPropertyDescription("Настройка уведомлений в Slack")
  SlackSetting slack = SlackSetting.EMPTY

  static class SlackSetting {
    static final SlackSetting EMPTY = new SlackSetting()

    @JsonPropertyDescription("Канал уведомлений")
    String channelName = "#build"
  }

}
