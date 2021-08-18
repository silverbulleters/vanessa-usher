/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
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

  String errorMessage = ""

  boolean showTestResults = false
  int successCount = 0
  int failedCount = 0
  int skippedCount = 0
}
