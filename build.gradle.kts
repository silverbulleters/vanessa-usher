val junitVersion = "5.6.1"

group = "org.github.silverbulleters"
version = "2.3.0"

plugins {
    java
    groovy
    id("com.mkobit.jenkins.pipelines.shared-library") version "0.10.1"
    id("com.github.ben-manes.versions") version "0.21.0"
    id ("com.github.hierynomus.license") version "0.15.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.jenkins-ci.org/releases/") }
}

dependencies {
    implementation("com.fasterxml.jackson.module", "jackson-module-jsonSchema", "2.9.8")

    implementation("org.codehaus.groovy", "groovy-all", "2.4.19")

    // unit-tests
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.6.1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.6.1")

    // https://repo.jenkins-ci.org/releases/com/lesfurets/jenkins-pipeline-unit/
    testImplementation("com.lesfurets:jenkins-pipeline-unit:1.12")

    testImplementation("org.assertj", "assertj-core", "3.15.0")

    // integration-tests
    integrationTestImplementation("org.spockframework", "spock-core", "1.3-groovy-2.4")
    integrationTestImplementation("org.codehaus.groovy", "groovy-all", "2.4.19")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8;
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }

    reports {
        html.isEnabled = true
    }
}

tasks.check {
    dependsOn(tasks.integrationTest)
}

// https://github.com/mkobit/jenkins-pipeline-shared-library-example/blob/master/build.gradle.kts
jenkinsIntegration {
    baseUrl.set(uri("http://localhost:5050").toURL())
    authentication.set(providers.provider { com.mkobit.jenkins.pipelines.http.AnonymousAuthentication })
    downloadDirectory.set(layout.projectDirectory.dir("jenkinsResources"))
}

// https://github.com/mkobit/jenkins-pipeline-shared-libraries-gradle-plugin
sharedLibrary {
    coreVersion.set(jenkinsIntegration.downloadDirectory.file("core-version.txt").map { it.asFile.readText().trim() })
    pluginDependencies {
        dependency("org.jenkins-ci.plugins", "pipeline-build-step", "2.9")
        dependency("org.6wind.jenkins", "lockable-resources", "2.5")
        val declarativePluginsVersion = "1.3.9"
        dependency("org.jenkinsci.plugins", "pipeline-model-api", declarativePluginsVersion)
        dependency("org.jenkinsci.plugins", "pipeline-model-declarative-agent", "1.1.1")
        dependency("org.jenkinsci.plugins", "pipeline-model-definition", declarativePluginsVersion)
        dependency("org.jenkinsci.plugins", "pipeline-model-extensions", declarativePluginsVersion)
    }
}

license {
    header = rootProject.file("license/header.txt")
    strictCheck = true
    ext["title"] = "Vanessa-Usher"
    ext["years"] = "2019-2022"
    ext["owner"] = "SilverBulleters, LLC"
    exclude("**/*.properties")
    exclude("**/*.xml")
    exclude("**/*.json")
    exclude("**/*.txt")
    exclude("**/*.java.orig")
    exclude("**/*.impl")
    exclude("**/*.java")
}
