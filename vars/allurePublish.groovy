import groovy.transform.Field
import org.silverbulleters.usher.config.JobConfiguration

@Field
JobConfiguration config

void call(JobConfiguration config) {
  boolean needPublish = config.getStages().isSyntaxCheck() || config.getStages().isSmoke() || config.getStages().isTdd() || config.getStages().isBdd()

  if (!needPublish) {
    return
  }

  this.config = config

  stage("Allure publish") {
    node(config.getAgent()) {
      checkout scm
      publish()
    }
  }

}

private def publish() {
  reports = []

  if (config.getStages().isSyntaxCheck()) {
    def path = getPrettyPath(config.getSyntaxCheckOptional().getAllurePath())
    reports.add([path: path])
  }

  if (config.getStages().isSmoke()) {
    def path = getPrettyPath(config.getSmokeOptional().getAllurePath())
    reports.add([path: path])
  }

  if (config.getStages().isTdd()) {
    def path = getPrettyPath(config.getTddOptional().getAllurePath())
    reports.add([path: path])
  }

  if (config.getStages().isBdd()) {
    def path = getPrettyPath(config.getBddOptional().getAllurePath())
    reports.add([path: path])
  }

  junit allowEmptyResults: true, testResults: '**/out/junit/*.xml '
  allure includeProperties: false, jdk: '', results: reports
}

private String getPrettyPath(String path) {
  if (path.startsWith("./")) {
    return path.substring(2)
  }
  return path
}