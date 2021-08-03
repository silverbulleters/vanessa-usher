import groovy.transform.Field
import org.silverbulleters.usher.config.JobConfiguration
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.config.stage.TddOptional

@Field
JobConfiguration config

@Field
TddOptional stageOptional

void call(JobConfiguration config) {
  if (!config.getStages().isTdd()) {
    return
  }

  this.config = config
  this.stageOptional = config.getTddOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения модульного тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          testing()
        }
        if (fileExists(stageOptional.getAllurePath())) {
          allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
        }
      }
    }
  }
}

private def testing() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentional.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credentional = credentional.getAuthString()
      def credentionalTestClient = getTestClient()
      xddTesting(credentional, credentionalTestClient)
    }
  } else {
    xddTesting('')
  }
}

private xddTesting(String credentional, String credentionalTestClient) {
  command = vrunner.xunit(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  command = command.replace("%credentionalTestClientID%", credentionalTestClient)
  cmdRun(command)
}

// FIXME: ДУБЛЬ
private String getTestClient() {
  def baseValue = '%s:%s:1538'
  login = "${USERNAME}"
  pass = ""
  try {
    pass = "${PASSWORD}"
  } catch (e) {
  }
  def credentionalTestClient = String.format('%s:%s:1538', login, pass)
  return credentionalTestClient
}
