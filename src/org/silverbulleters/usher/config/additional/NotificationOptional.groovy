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
