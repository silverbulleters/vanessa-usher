/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.state.PipelineState

/**
 * Распаковать информационную базу, если нужно
 * @param args
 */
void unpackInfobase(Map args) {
  if (args.config.stages.prepareBase && args.state.prepareBase.localBuildFolder) {
    logger.info('Распаковка локальной информационной базы"')
    unstash 'build-ib-folder'
  }
}

/**
 * Упаковать локальную информационную базу
 */
void packInfobase(PipelineState state) {
  if (fileExists('build/ib')) {
    state.prepareBase.localBuildFolder = true
    stash name: 'build-ib-folder', includes: 'build/ib/*'
  }
}