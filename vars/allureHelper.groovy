public void createAllureCategories(String title, String allurePath) {
  // нужен плагин https://plugins.jenkins.io/pipeline-utility-steps/
  def categories = [['name': title]]
  writeJSON file: "${allurePath}/categories.json", json: categories
}