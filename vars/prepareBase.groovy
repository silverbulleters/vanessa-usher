import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.PrepareBaseOptional

@Field
PipelineConfiguration config

@Field
PrepareBaseOptional stageOptional

/**
 * Подготовка и обновление базы
 *
 * @param config
 */
void call(PipelineConfiguration config) {
  if (!config.getStages().isPrepareBase()) {
    return
  }

  this.config = config
  this.stageOptional = config.getPrepareBaseOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage('Prepare base') {
      node(config.getAgent()) {
        checkout scm
        prepare()
      }
    }
  }
}

private def prepare() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentional.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credentional = credentional.getAuthString()
      prepareInternal(credentional)
    }
  } else {
    prepareInternal('')
  }
}

private def prepareInternal(String credentional) {
  if (stageOptional.getTemplate().isEmpty()) {
    if (stageOptional.getRepo() != Repo.EMPTY) {
      loadRepo(credentional)
      updateDB(credentional)
    } else {
      initDevFromSource(credentional)
    }
  } else {
    initDevWithTemplate(credentional)
    compile(credentional)
  }
  migrate(credentional)
}

private def loadRepo(credentional) {
  command = vrunner.loadRepo(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  auth = stageOptional.getRepo().getAuth()
  withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
    def credentionalRepo = credentional.getAuthRepoString()
    command = command.replace("%credentionalStorageID%", credentionalRepo)
    cmdRun(command)
  }
}

private def updateDB(credentional) {
  command = vrunner.updateDB(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}

private def initDevWithTemplate(credentional) {
  command = vrunner.initDevWithTemplate(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}

private def initDevFromSource(credentional) {
  command = vrunner.initDevFromSource(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}

private def compile(credentional) {
  command = vrunner.initDevWithTemplate(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}

private def migrate(credentional) {
  command = vrunner.migrate(config, stageOptional)
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}