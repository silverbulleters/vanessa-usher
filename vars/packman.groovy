/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration

public void setDatabase(PipelineConfiguration config, String credential) {
  def command = [
      "packman",
      "set-database", infobaseHelper.getConnectionString(config),
      "%credentialID%"
  ].join(" ")
  command = command.replace("%credentialID%", credential)
  cmdRun(command)
}

public void makeCf(PipelineConfiguration config) {
  def command = [
      "packman",
      "make-cf",
      "-v8version", config.getV8Version()
  ].join(" ")
  cmdRun(command)
}