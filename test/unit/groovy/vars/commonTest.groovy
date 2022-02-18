/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package vars

import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.TestHelper

class commonTest extends BasePipelineTest {
  private static final BASE_PATH = 'test/unit/resources/gitsync'

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp()
    TestHelper.registerUsher2(this)

    def contentVersion = """
    <?xml version="1.0" encoding="UTF-8"?>
    <VERSION>40</VERSION>
    """

    helper.addFileExistsMock("${BASE_PATH}/src/cf/VERSION", true)
    helper.addReadFileMock("${BASE_PATH}/src/cf/VERSION", contentVersion)
  }

  @Test
  void "check repo version form file"() {
    def script = """
    def version = common.getRepoVersion('${BASE_PATH}/src/cf')
    cmdRun("echo version=" + version)
    """
    runInlineScript(script)
    printCallStack()
    assertCallStack().contains('cmdRun(echo version=40)')
  }

  @Test
  void "check repo version without file"() {
    def script = """
    def version = common.getRepoVersion('${BASE_PATH}/src')
    if (version == '') {
      cmdRun("echo version is empty")
    }
    """
    runInlineScript(script)
    printCallStack()
    assertCallStack().contains('cmdRun(echo version is empty)')
  }

}
