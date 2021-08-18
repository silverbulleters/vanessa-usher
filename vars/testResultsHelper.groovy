/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.silverbulleters.usher.config.PipelineConfiguration

void archive(PipelineConfiguration config, stageOptional) {
  dir(stageOptional.getAllurePath()) {
    stash includes: '*', name: "${stageOptional.getId()}-allure"
    deleteDir()
  }
  dir(config.getJunitPath()) {
    stash includes: "*", name: "${stageOptional.getId()}-junit"
    deleteDir()
  }
}