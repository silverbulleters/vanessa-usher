import org.silverbulleters.usher.config.JobConfiguration
import org.silverbulleters.usher.config.ConfigurationReader
import org.silverbulleters.usher.ioc.ContextRegistry

JobConfiguration call(String pathToConfig) {
  ContextRegistry.registerDefaultContext(this)

  if (fileExists(pathToConfig)) {
    def content = readFile(pathToConfig)
    return ConfigurationReader.create(content)
  } else {
    println("Конфигурационный файл не найден")
  }
  return ConfigurationReader.create()
}