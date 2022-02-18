/*
 * Vanessa-Usher
 * Copyright (C) 2019-2022 SilverBulleters, LLC - All Rights Reserved.
 * Unauthorized copying of this file in any way is strictly prohibited.
 * Proprietary and confidential.
 */
import com.mkobit.jenkins.pipelines.codegen.LocalLibraryRetriever
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule

class cmdRunTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule()

    @Before
    void configureGlobalLibraries() {
        jenkinsRule.timeout = 30
        def retriever = new LocalLibraryRetriever()
        def localLibrary = new LibraryConfiguration('vanessa-usher', retriever)
        localLibrary.implicit = true
        localLibrary.defaultVersion = 'unused'
        localLibrary.allowVersionOverride = false
        GlobalLibraries.get().libraries = [localLibrary]
    }

    @Test
    void "cmdRun echo message"() {
        def script = getPipelineScript()

        def flowDefinition = new CpsFlowDefinition(script, true)
        def workflowJob = jenkinsRule.createProject(WorkflowJob, 'project')
        workflowJob.definition = flowDefinition

        jenkinsRule.assertLogContains('Check', jenkinsRule.buildAndAssertSuccess(workflowJob))
    }

    static String getPipelineScript() {
        return '''
        pipeline {
            agent any
            stages {
                stage('test') {
                    steps {
                        cmdRun("echo Check")
                    }
                }
            }
        }
        '''.stripIndent()
    }
}
