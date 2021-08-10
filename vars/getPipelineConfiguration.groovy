import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.ConfigurationReader
import org.silverbulleters.usher.ioc.ContextRegistry

PipelineConfiguration call(String pathToConfig) {
  ContextRegistry.registerDefaultContext(this)

  if (fileExists(pathToConfig)) {
    def content = readFile(pathToConfig)
    return ConfigurationReader.create(content)
  } else {
    throw new Exception("Конфигурационный файл не найден")
  }
}