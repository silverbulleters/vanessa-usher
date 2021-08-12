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