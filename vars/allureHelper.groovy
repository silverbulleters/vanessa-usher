/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
/**
 * Добавить в результаты отчеты Allure настройку категорий
 * @param title заголовок категории
 * @param allurePath путь к каталогу с отчетом в формате Allure
 */
void addCategories(String title, String allurePath) {
  def categories = [['name': title]]
  writeJSON file: "${allurePath}/categories.json", json: categories
}