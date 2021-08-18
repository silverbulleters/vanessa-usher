/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration

static String getEmojiStatusForEmail(String status) {
  if (status == 'SUCCESS') {
    return "✅"
  }
  return "❌"
}

static String getEmojiStatusForSlack(String status) {
  if (status == 'SUCCESS') {
    return ":white_check_mark:"
  }
  return ":x:"
}

static boolean needPublishTests(PipelineConfiguration config) {
  return config.getStages().isSyntaxCheck() || config.getStages().isSmoke() || config.getStages().isTdd() || config.getStages().isBdd()
}