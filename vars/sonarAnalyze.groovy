import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.SonarQubeOptional

@Field
PipelineConfiguration config

@Field
SonarQubeOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isSonarqube()) {
    return
  }

  this.config = config
  this.stageOptional = config.getSonarQubeOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage('Sonarqube static analysis') {
      node(stageOptional.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения sonar-scanner', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          analyze()
        }
      }
    }
  }
}

private def analyze() {
  def projectVersion = getProjectVersion()
  def scannerHome = tool stageOptional.getToolId()

  def sonarDebugKey = ''
  if (stageOptional.isDebug()) {
    sonarDebugKey = '-X'
  }
  sonarCommand = "${scannerHome}/bin/sonar-scanner ${sonarDebugKey} ${projectVersion}"
  withSonarQubeEnv("${stageOptional.getServerId()}") {
    cmdRun(sonarCommand)
  }
}

private String getProjectVersion() {
  return ''
}