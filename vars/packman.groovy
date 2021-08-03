import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

public void setDatabase(PipelineConfiguration config, String credential) {
  def command = [
      "packman",
      "set-database", infobaseHelper.getConnectionString(config),
      "%credentialID%"
  ].join(" ")
  command = command.replace("%credentialID%", credential)
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