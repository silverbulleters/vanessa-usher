import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

public void setDatabase(PipelineConfiguration config, String credentional) {
  def command = [
      "packman",
      "set-database", infobaseHelper.getConnectionString(config),
      "%credentionalID%"
  ].join(" ")
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}

public void makeCf(PipelineConfiguration config) {
  def command = [
      "packman",
      "make-cf",
      "-v8version", config.getV8Version()
  ].join(" ")
  cmdRun(command)
}