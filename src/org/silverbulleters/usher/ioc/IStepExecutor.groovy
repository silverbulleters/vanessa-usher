package org.silverbulleters.usher.ioc

interface IStepExecutor {
    int sh(String command)
    void error(String message)
}
