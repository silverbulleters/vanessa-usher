/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

class NotificationOptional {
  static final NotificationOptional EMPTY = new NotificationOptional()

  NotificationMode mode = NotificationMode.NO_USE
  String email = "test@localhost"
  SlackSetting slack = SlackSetting.EMPTY

  static class SlackSetting {
    static final SlackSetting EMPTY = new SlackSetting()
    String channelName = "#build"
  }

}
