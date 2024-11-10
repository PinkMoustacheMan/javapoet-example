import org.gradle.jvm.toolchain.JavaLanguageVersion.of
import org.gradle.jvm.toolchain.JvmVendorSpec.ADOPTIUM

plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain {
        languageVersion = of(21)
        vendor = ADOPTIUM
    }
}
