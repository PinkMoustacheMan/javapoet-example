pluginManagement {
    repositories {
        mavenLocal { mavenContent { snapshotsOnly() } }
        mavenCentral { mavenContent { releasesOnly() } }
        gradlePluginPortal()
        includeBuild("gradle/plugins")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal { mavenContent { snapshotsOnly() } }
        mavenCentral { mavenContent { releasesOnly() } }
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "javapoet-example"

include(
    "javapoet-example-processor",
    "javapoet-example-test"
)
