import org.silverbulleters.usher.config.JobConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

public void setDatabase(JobConfiguration config, String credentional) {
  def command = [
      "packman",
      "set-database", infobaseHelper.getConnectionString(config),
      "%credentionalID%"
  ].join(" ")
  command = command.replace("%credentionalID%", credentional)
  cmdRun(command)
}

public void makeCf(JobConfiguration config) {
  def command = [
      "packman",
      "make-cf",
      "-v8version", config.getV8Version()
  ].join(" ")
  cmdRun(command)
}