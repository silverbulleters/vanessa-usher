import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.BuildOptional
import org.silverbulleters.usher.config.additional.InfoBase

@Field
PipelineConfiguration config

@Field
BuildOptional stageOptional

void call(PipelineConfiguration config) {
  if (!config.getStages().isBuild()) {
    return
  }

  this.config = config
  this.stageOptional = config.getBuildOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время сборки поставки', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          runBuild()
          archiving()
        }
      }
    }
  }

}

private void runBuild() {
  def auth = config.getDefaultInfobase().getAuth()
  if (!auth.isEmpty() && credentialHelper.exist(auth)) {
    withCredentials([usernamePassword(credentialsId: auth, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
      def credential = credentialHelper.getCustomAuth("-db-user", "-db-pwd")
      packman.setDatabase(config, credential)
      packman.makeCf(config)
    }
  } else {
    packman.setDatabase(config, '')
    packman.makeCf(config)
  }
}

private void archiving() {
  if (fileExists(stageOptional.getDistPath())) {
    archiveArtifacts stageOptional.getDistPath()
  }
}