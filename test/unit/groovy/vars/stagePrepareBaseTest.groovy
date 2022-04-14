/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
package vars

import com.lesfurets.jenkins.unit.BasePipelineTest
import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.TestHelper

class stagePrepareBaseTest extends BasePipelineTest {
  private static final BASE_PATH = "test/unit/resources/prepareBase"

  @Override
  @BeforeEach
  void setUp() throws Exception {
    super.setUp()
    TestHelper.registerUsher2(this)

    helper.registerAllowedMethod("catchError", [Map, Closure], { Map args, Closure c ->
      c.delegate = delegate
      helper.callClosure(c)
    })
  }

  // FIXME: отключен до починки контекста в BasePipelineTest
//  @Test
//  void "check loadrepo with version"() {
//    def script = """
//    def config = getPipelineConfiguration()
//    config.stages.prepareBase = true
//    config.prepareBaseOptional.repo.path = 'tcp://localhost/repo'
//    config.prepareBaseOptional.sourcePath = '${BASE_PATH}/src/cf'
//
//    def state = newPipelineState()
//
//    stagePrepareBase(config, state)
//    """
//    runInlineScript(script)
//    printCallStack()
//
//    def pathOfCommand = "cmdRun(vrunner loadrepo  --settings ./tools/JSON/vRunner.json --ibconnection /F.build/ib --storage-user USERNAME --storage-pwd PASSWORD  --v8version 8.3 --storage-name tcp://localhost/repo --nocacheuse"
//    def example = "${pathOfCommand} --storage-ver 40)"
//    assertCallStack().contains(example)
//
//    example = "${pathOfCommand})"
//    assertCallStack().doesNotContain(example)
//  }
//
//  @Test
//  void "check loadrepo without version"() {
//    def script = """
//    def config = getPipelineConfiguration()
//    config.stages.prepareBase = true
//    config.prepareBaseOptional.repo.path = 'tcp://localhost/repo'
//    config.prepareBaseOptional.sourcePath = '${BASE_PATH}/src'
//
//    def state = createPipelineState()
//
//    node {
//      prepareInfobase(config, state)
//    }
//    """
//    runInlineScript(script)
//    printCallStack()
//
//    def command = "cmdRun(vrunner loadrepo  --settings ./tools/JSON/vRunner.json --ibconnection /F.build/ib --storage-user USERNAME --storage-pwd PASSWORD  --v8version 8.3 --storage-name tcp://localhost/repo --nocacheuse)"
//    assertCallStack().contains(command)
//  }

}
