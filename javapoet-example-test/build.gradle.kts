plugins {
    id("build-commons")
}

dependencies {
    annotationProcessor(project(":javapoet-example-processor"))
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
