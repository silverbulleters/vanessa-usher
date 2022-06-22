/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package org.silverbulleters.usher.config.additional

import com.fasterxml.jackson.annotation.JsonPropertyDescription

/**
 * Режим уведомлений
 */
enum NotificationMode {
  @JsonPropertyDescription("Не использовать уведомления")
  NO_USE,
  @JsonPropertyDescription("Уведомления по email")
  EMAIL,
  @JsonPropertyDescription("Уведомления в Slack")
  SLACK,
  @JsonPropertyDescription("Уведомления в Telegram")
  TELEGRAM
}