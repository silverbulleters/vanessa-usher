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


  boolean showTestResults = false
  int successCount = 0
  int failedCount = 0
  int skippedCount = 0
}
