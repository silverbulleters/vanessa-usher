/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
/**
 * Удалить каталог в рабочей области
 * @param path путь к каталогу
 */
void call(String path) {
  if (!path.isEmpty()) {
    dir(path) {
      deleteDir()
    }
  }
}
