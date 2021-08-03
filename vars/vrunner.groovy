import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.stage.BddOptional
import org.silverbulleters.usher.config.stage.PrepareBaseOptional
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.stage.SmokeOptional
import org.silverbulleters.usher.config.stage.SyntaxCheckOptional
import org.silverbulleters.usher.config.stage.TddOptional

def initDevFromSource(PipelineConfiguration config, PrepareBaseOptional optional) {
  def command = [
      "vrunner",
      "init-dev",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--src=${optional.getSourcePath()}",
      "--v8version", config.getV8Version(),
      "--nocacheuse"
  ]
  return command.join(" ")
}

def initDevWithTemplate(PipelineConfiguration config, PrepareBaseOptional optional) {
  // TODO: проверить существование `optional.getTemplate()`
  def command = [
      "vrunner",
      "init-dev",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--dt", optional.getTemplate(),
      "--v8version", config.getV8Version(),
      "--nocacheuse"
  ]
  return command.join(" ")
}

def compile(PipelineConfiguration config, PrepareBaseOptional optional) {
  def command = [
      "vrunner",
      "compile",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--src=${optional.getSourcePath()}",
      "-c",
      "--noupdate",
      "--v8version", config.getV8Version(),
      "--nocacheuse"
  ]
  return command.join(" ")
}

def loadRepo(PipelineConfiguration config, PrepareBaseOptional optional) {
  def command = [
      "vrunner",
      "loadrepo",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "%credentialStorageID%",
      "--v8version", config.getV8Version(),
      "--storage-name", optional.getRepo().getPath(),
      "--nocacheuse"
  ]
  return command.join(" ")
}

def updateDB(PipelineConfiguration config, PrepareBaseOptional optional) {
  def command = [
      "vrunner",
      "updatedb",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--v8version", config.getV8Version(),
      "--nocacheuse"
  ]
  return command.join(" ")
}

def migrate(PipelineConfiguration config, PrepareBaseOptional optional) {
  def command = [
      "vrunner",
      "run",
      "%credentialID%",
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--settings", config.getVrunnerConfig(),
      "--command", "'ЗапуститьОбновлениеИнформационнойБазы;ЗавершитьРаботуСистемы;'",
      "--execute", '$runnerRoot/epf/ЗакрытьПредприятие.epf'
  ]
  return command.join(" ")
}

def syntaxCheck(PipelineConfiguration config, SyntaxCheckOptional optional) {
  def command = [
      "vrunner",
      "syntax-check",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--allure-results2", optional.getAllurePath(),
      "--junitpath", optional.getJunitPath(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--v8version", config.getV8Version()
  ]
  return command.join(" ")
}

def smoke(PipelineConfiguration config, SmokeOptional optional) {
  def pathToAllure = "${optional.getAllurePath()}/allure-smoke.xml"
  def command = [
      "vrunner",
      "xunit",
      optional.getTestPath(),
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--v8version", config.getV8Version(),
      "--testclient", "%credentialTestClientID%",
      "--reportsxunit", "ГенераторОтчетаJUnitXML{${optional.getJunitPath()}};ГенераторОтчетаAllureXMLВерсия2{${pathToAllure}}",
      "--xddExitCodePath", "./xddExitCodePath.txt",
      "--xddConfig", optional.getXddConfig()
  ]
  return command.join(" ")
}

def xunit(PipelineConfiguration config, TddOptional optional) {
  def pathToAllure = "${optional.getAllurePath()}/allure-xdd.xml"
  def command = [
      "vrunner",
      "xunit",
      optional.getTestPath(),
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--v8version", config.getV8Version(),
      "--testclient", "%credentialTestClientID%",
      "--reportsxunit", "ГенераторОтчетаJUnitXML{${optional.getJunitPath()}};ГенераторОтчетаAllureXMLВерсия2{${pathToAllure}}",
      "--xddExitCodePath", "./xddExitCodePath.txt",
      "--xddConfig", optional.getXddConfig()
  ]
  return command.join(" ")
}

def vanessa(PipelineConfiguration config, BddOptional optional) {
  def command = [
      "vrunner",
      "vanessa",
      "%credentialID%",
      "--settings", config.getVrunnerConfig(),
      "--ibconnection", infobaseHelper.getConnectionString(config),
      "--v8version", config.getV8Version()
  ]
  return command.join(" ")
}