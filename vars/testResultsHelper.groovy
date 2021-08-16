import org.silverbulleters.usher.config.PipelineConfiguration

void archive(PipelineConfiguration config, stageOptional) {
  dir(stageOptional.getAllurePath()) {
    stash includes: '*', name: "${stageOptional.getId()}-allure"
    deleteDir()
  }
  dir(config.getJunitPath()) {
    stash includes: "*", name: "${stageOptional.getId()}-junit"
    deleteDir()
  }
}