import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") apply false
}

val versionNumber = System.getenv("version")?.substringAfter("R-") ?: "dev"

allprojects {
    group = "io.github.4sh.ocpi-lib"
    version = versionNumber

    repositories {
        mavenCentral()
    }
}

subprojects {
    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
            languageVersion = "1.6"
            apiVersion = "1.6"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        ignoreFailures = true
    }
}