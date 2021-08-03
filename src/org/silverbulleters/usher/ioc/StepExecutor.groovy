package org.silverbulleters.usher.ioc

import org.jenkinsci.plugins.workflow.cps.EnvActionImpl
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

class StepExecutor implements IStepExecutor {
    private steps

    public EnvActionImpl env
    public RunWrapper currentBuild
    public Map params

    StepExecutor(steps) {
        this.steps = steps
        this.env = this.steps.env
        this.currentBuild = this.steps.currentBuild
        this.params = Collections.unmodifiableMap(this.steps.params)
    }

    @Override
    int sh(String command) {
        this.steps.sh returnStatus: true, script: "${command}"
    }

    @Override
    void error(String message) {
        this.steps.currentBuild.setResult(JobStatus.Failure)
        this.steps.error(message)
    }
}
