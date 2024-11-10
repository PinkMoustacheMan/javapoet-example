pluginManagement {
    repositories {
        mavenLocal { mavenContent { snapshotsOnly() } }
        mavenCentral { mavenContent { releasesOnly() } }
        gradlePluginPortal()
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

rootProject.name = "plugins"
