/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
/**
 * Вызвать failure для stage
 */
void call() {
  cmdRun('exit 1')
}

/**
 * Вызвать failure для stage с текстом ошибки
 */
void call(String message) {
  logger.error(message)
  call()
}