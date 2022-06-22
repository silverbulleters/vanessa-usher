/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.junit.jupiter.api.Test
import org.silverbulleters.usher.config.ConfigurationReader
import org.silverbulleters.usher.config.Stages
import org.silverbulleters.usher.config.additional.InfoBase
import org.silverbulleters.usher.config.stage.*

import static org.assertj.core.api.Assertions.assertThat

class ConfigurationReaderTest {

  @Test
  void "Check default configuration"() {
    def config = ConfigurationReader.create()

    assertThat(config.getAgent()).isEqualTo("any")
    assertThat(config.getV8Version()).isEqualTo("8.3")
    assertThat(config.isDebug()).isFalse()
    assertThat(config.getTimeout()).isEqualTo(100)
    checkStages(config.getStages())
  }

  private static void checkStages(Stages stages) {
    assertThat(stages).isNotNull()
    assertThat(stages.isGitsync()).isFalse()
    assertThat(stages.isPrepareBase()).isFalse()
    assertThat(stages.isCheckExtensions()).isFalse()
    assertThat(stages.isSyntaxCheck()).isFalse()
    assertThat(stages.isSmoke()).isFalse()
    assertThat(stages.isTdd()).isFalse()
    assertThat(stages.isBdd()).isFalse()
    assertThat(stages.isSonarqube()).isFalse()
    assertThat(stages.isBuild()).isFalse()
    assertThat(stages.isEdtTransform()).isFalse()
    assertThat(stages.isYard()).isFalse()
  }

}
