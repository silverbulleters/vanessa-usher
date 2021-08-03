import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.GitsyncOptional
import org.silverbulleters.usher.config.additional.InfoBase

@Field
PipelineConfiguration config

@Field
GitsyncOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isGitsync()) {
    return
  }

  this.config = config
  this.stageOptional = config.getGitsyncOptional()

  timeout(unit: 'MINUTES', time: config.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        syncInternal()
      }
    }
  }
}

private def syncInternal() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentional.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credentional = credentional.getAuthString()
      runSync(credentional)
    }
  } else {
    runSync('')
  }
}

private def runSync(String credentional) {
  def connectionString = getConnectionString()
  def command = "gitsync %credentionalID% --v8version ${config.getV8Version()} --ibconnection ${connectionString} all ${stageOptional.getConfigPath()}"
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
  // TODO: ищем ошибку "КРИТИЧНАЯОШИБКА" в логах, если есть - фейлим сборку этой ошибкой
}

// FIXME: ДУБЛЬ!
private def getConnectionString() {
  def connectionString = ""
  if (config.getDefaultInfobase() != InfoBase.EMPTY) {
    connectionString = config.getDefaultInfobase().getConnectionString()
  }
  return connectionString;
}