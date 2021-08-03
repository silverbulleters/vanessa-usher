import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.config.stage.SmokeOptional

@Field
PipelineConfiguration config

@Field
BddOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isBdd()) {
    return
  }

  this.config = config
  this.stageOptional = config.getBddOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения bdd тестирования', buildResult: 'FAILURE', stageResult: 'FAILURE') {
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
      bddTesting(credentional)
    }
  } else {
    bddTesting('')
  }
}

private bddTesting(String credentional) {
  command = vrunner.vanessa(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}