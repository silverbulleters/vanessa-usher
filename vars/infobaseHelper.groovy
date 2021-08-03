import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

String getConnectionString(PipelineConfiguration config) {
  def connectionString = ""
  if (config.getDefaultInfobase() != InfoBase.EMPTY) {
    connectionString = config.getDefaultInfobase().getConnectionString()
  }
  return connectionString;
}