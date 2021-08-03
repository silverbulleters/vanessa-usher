import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional

@Field
PipelineConfiguration config

@Field
SyntaxCheckOptional stageOptional

def call(PipelineConfiguration config) {
  if (!config.getStages().isSyntaxCheck()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSyntaxCheckOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения синтаксической проверки', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          check()
        }
        if (fileExists(stageOptional.getAllurePath())) {
          allureHelper.createAllureCategories(stageOptional.getName(), stageOptional.getAllurePath())
        }
      }
    }
  }
}

private def check() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentional.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credentional = credentional.getAuthString()
      runSyntaxCheck(credentional)
    }
  } else {
    runSyntaxCheck('')
  }
}

private def runSyntaxCheck(credentional) {
  command = vrunner.syntaxCheck(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}