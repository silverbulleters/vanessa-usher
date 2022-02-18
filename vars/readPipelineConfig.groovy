/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import groovy.transform.Field
import org.silverbulleters.usher.config.ConfigurationReader
import org.silverbulleters.usher.config.PipelineConfiguration
import org.silverbulleters.usher.util.Common
import org.silverbulleters.usher.util.GitlabHelper

@Field
PipelineConfiguration config

/**
 * Прочитать конфигурацию конвейера
 * @param pathToConfig путь к конфигурации
 * @param nodeForReadConfig имя или метка узла для чтения конфигурации
 * @return конфигурация конвейера
 */
PipelineConfiguration call(String pathToConfig, String nodeForReadConfig) {

  logger.info("Чтение конфигурационного файла")

  try {
    readConfigByApi(pathToConfig)
  } catch (def exception) {
    logger.info("Не удалось прочитать конфигурационный файл по API. Причина: ${exception.getMessage()}")
  }

  if (config == null) {

    logger.debug("Чтение конфигурационного файла на узле с checkout scm")

    readConfigInternal(nodeForReadConfig) {
      config = getPipelineConfiguration(pathToConfig)
    }

  }

  return config
}

private void readConfigByApi(pathToConfig) {
  def scmUrl = scm.getUserRemoteConfigs()[0].getUrl()

  if (scmUrl != null) {

    if (GitlabHelper.isUrlGitlab(scmUrl)) {

      logger.info("Чтение конфигурационного файла из Gitlab по API")

      def scmCredentialsId = scm.getUserRemoteConfigs()[0].getCredentialsId()
      def shaCommit = Common.getShaCommitFromLog(currentBuild.rawBuild.getLog())

      withCredentials([usernamePassword(credentialsId: scmCredentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        def content = GitlabHelper.getContent(scmUrl, PASSWORD, shaCommit, pathToConfig)

        config = ConfigurationReader.create(content)

      }

    }

  }

}

private void readConfigInternal(String label, Closure body) {

  if (label.isEmpty()) {
    node {
      checkout scm
      body()
    }
  } else {
    node(label) {
      checkout scm
      body()
    }
  }

}
