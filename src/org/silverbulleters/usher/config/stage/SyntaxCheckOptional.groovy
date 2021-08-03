package org.silverbulleters.usher.config.stage

class SyntaxCheckOptional extends BaseOptional {
    static final EMPTY = new SyntaxCheckOptional()

    String allurePath = "./out/syntaxCheck/allure"
    String junitPath = "./out/junit/syntaxCheck.xml"

    SyntaxCheckOptional() {
        name = "Syntax check"
        timeout = 100
    }

}
