import groovy.transform.Field
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.EdtTransformOptional

@Field
PipelineConfiguration config

@Field
EdtTransformOptional stageOptional

def call(PipelineConfiguration config) {
  if (!config.getStages().isEdtTransform()) {
    return
  }

  this.config = config
  this.stageOptional = config.getEdtTransformOptional()

  timeout(unit: 'MINUTES', time: stageOptional.getTimeout()) {
    stage(stageOptional.getName()) {
      node(config.getAgent()) {
        checkout scm
        catchError(message: 'Ошибка во время выполнения трансформации EDT в XML', buildResult: 'FAILURE', stageResult: 'FAILURE') {
          def workspace = pwd()
          clearSpace()
          transform(workspace)
        }
      }
    }
  }

}

private void transform(String workspace) {
  def command = ring.workspaceExport(workspace, stageOptional)
  cmdRun(command)

  if (!fileExists(stageOptional.getOutPath())) {
    throw new Exception("Трансформация прошла неуспешно. Результирующий каталог с XML не существует")
  }
}

private void clearSpace() {
  clearDirectory(stageOptional.getOutPath())
  clearDirectory(stageOptional.getWorkspacePath())
}

private void clearDirectory(String path) {
  if (!path.isEmpty()) {
    dir(path) {
      deleteDir()
    }
  }
}