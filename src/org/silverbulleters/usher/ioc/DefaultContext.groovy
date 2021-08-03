package org.silverbulleters.usher.ioc

class DefaultContext implements IContext, Serializable {
    private steps

    DefaultContext(steps) {
        this.steps = steps
    }

    @Override
    IStepExecutor getStepExecutor() {
        return new StepExecutor(this.steps)
    }
}
