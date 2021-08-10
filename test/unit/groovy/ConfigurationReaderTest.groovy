/*
 * Vanessa-Usher
 * Copyright (C) 2019-2021 SilverBulleters, LLC - All Rights Reserved.
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
    assertThat(config.getDefaultInfobase()).isEqualTo(InfoBase.EMPTY)
    checkStages(config.getStages());

    assertThat(config.getGitsyncOptional()).isEqualTo(GitsyncOptional.EMPTY)

    assertThat(config.getPrepareBaseOptional()).isEqualTo(PrepareBaseOptional.EMPTY)
    assertThat(config.getSyntaxCheckOptional()).isEqualTo(SyntaxCheckOptional.EMPTY)
    assertThat(config.getSmokeOptional()).isEqualTo(SmokeOptional.EMPTY)
    assertThat(config.getTddOptional()).isEqualTo(TddOptional.EMPTY)
    assertThat(config.getBddOptional()).isEqualTo(BddOptional.EMPTY)
    assertThat(config.getSonarQubeOptional()).isEqualTo(SonarQubeOptional.EMPTY)
    assertThat(config.getBuildOptional()).isEqualTo(BuildOptional.EMPTY)
  }

  private static void checkStages(Stages stages) {
    assertThat(stages).isNotNull()
    assertThat(stages.isGitsync()).isFalse()
    assertThat(stages.isPrepareBase()).isFalse()
    assertThat(stages.isSyntaxCheck()).isFalse()
    assertThat(stages.isSmoke()).isFalse()
    assertThat(stages.isTdd()).isFalse()
    assertThat(stages.isBdd()).isFalse()
    assertThat(stages.isSonarqube()).isFalse()
    assertThat(stages.isBuild()).isFalse()
  }

}
