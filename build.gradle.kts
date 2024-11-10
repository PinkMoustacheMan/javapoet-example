import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.BIN

plugins {
    id("java")
    id("wrapper")
}

group = "com.example"
version = "0.1.0-SNAPSHOT"

tasks {
    named<Wrapper>("wrapper") {
        gradleVersion = "8.10.2"
        distributionType = BIN
    }
}
