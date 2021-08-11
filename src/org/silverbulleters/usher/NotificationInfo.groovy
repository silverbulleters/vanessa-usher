package org.silverbulleters.usher

class NotificationInfo {
  String channelId = ""
  String status = ""
  String projectName = ""
  String buildNumber = ""
  String buildUrl = ""
  String branchName = "<Не заполнено>"
  String commitId = "<Не заполнено>"
  String commitMessage = "<Не заполнено>"

  int successCount = 0
  int failedCount = 0
  int skippedCount = 0

  String icon() {
    if (status == "SUCCESS") {
      return ":white_check_mark:"
    }
    return ":x:"
  }
}
