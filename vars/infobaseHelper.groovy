/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.config.additional.InfoBase

String getConnectionString(PipelineConfiguration config) {
  def connectionString = ""
  if (config.getDefaultInfobase() != InfoBase.EMPTY) {
    connectionString = config.getDefaultInfobase().getConnectionString()
  }
  return connectionString;
}