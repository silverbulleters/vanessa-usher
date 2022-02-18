/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import org.junit.jupiter.api.Test
import org.silverbulleters.usher.util.Common

import static org.assertj.core.api.Assertions.assertThat

class CommonTest {

  @Test
  void "Check project version"() {
    def version = Common.getLibraryVersion()
    def versionFromBuild = versionFromBuild()
    assertThat(version).isEqualTo(versionFromBuild)
  }

  private static String versionFromBuild() {
    def content = new File('./build.gradle.kts').getText('UTF-8')
    return (content =~ /version.=."(.*)"/)[0][1]
  }

}
