/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
void createAllureCategories(String title, String allurePath) {
  // нужен плагин https://plugins.jenkins.io/pipeline-utility-steps/
  def categories = [['name': title]]
  writeJSON file: "${allurePath}/categories.json", json: categories
}