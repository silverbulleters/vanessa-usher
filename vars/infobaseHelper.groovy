import org.silverbulleters.usher.config.JobConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

String getConnectionString(JobConfiguration config) {
  def connectionString = ""
  if (config.getDefaultInfobase() != InfoBase.EMPTY) {
    connectionString = config.getDefaultInfobase().getConnectionString()
  }
  return connectionString;
}