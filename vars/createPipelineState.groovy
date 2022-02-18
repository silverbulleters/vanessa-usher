/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.state.PipelineState

/**
 * Создать новое состояние конвейера
 * @return состояние конвейера
 */
PipelineState call() {
  logger.debug("Инициализация состояния конвейера")
  return new PipelineState()
}
