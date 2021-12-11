/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.UsherConstant
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

String getConnectionString(PipelineConfiguration config) {
  def connectionString = ""
  if (config.getDefaultInfobase().getConnectionString() != UsherConstant.EMPTY_VALUE) {
    connectionString = config.getDefaultInfobase().getConnectionString()
  }
  return connectionString;
}

/**
 * Распаковать информационную базу, если нужно
 * @param args
 */
void unzipInfobase(Map args) {
  if (args.config.stages.prepareBase && args.state.prepareBase.localBuildFolder) {
    logger.info('Распаковка локальной информационной базы"')
    unstash 'build-ib-folder'
  }
}