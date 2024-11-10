plugins {
    id("maven-publish")
    id("build-commons")
}

dependencies {
    compileOnly("io.avaje:avaje-prisms:1.34")
    annotationProcessor("io.avaje:avaje-prisms:1.34")

    implementation("com.palantir.javapoet:javapoet:1.0.0-SNAPSHOT")
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs.addAll(
            listOf(
                "-proc:full",
            )
        )
    }
}
