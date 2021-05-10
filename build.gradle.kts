import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.13.0")
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.4.32"

    id("com.diffplug.spotless") version "5.12.4"
    id("io.gitlab.arturbosch.detekt") version "1.16.0"

    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
    id("com.vanniktech.maven.publish") version "0.13.0" apply false
}

repositories {
    mavenCentral()
    jcenter()
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/**.kt")

            ktlint(libs.versions.ktlint.get())

            licenseHeaderFile(rootProject.file("spotless/license.kt"))
        }
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        input = files("$projectDir/src")
        config = files("$rootDir/detekt.yml")

        failFast = true
        autoCorrect = false
    }
}

val detektCheck by tasks.registering(Detekt::class) {
    description = "Runs a failfast detekt build."
    parallel = true
    buildUponDefaultConfig = true

    setSource(files(
        "$projectDir/interactor/src",
        "$projectDir/logging-api/src",
        "$projectDir/logging/src"
    ))
    config.setFrom("$rootDir/detekt.yml")

    include("**/*.kt")
    include("**/*.kts")
    exclude(".*test.*")
    exclude("**/resources/**")
    exclude("**/tmp/**")
    exclude(".*/res/.*")
    exclude("**/build/**")
}

apply<nl.elements.mobilization.AndroidMetaModulePlugin>()
apply<nl.elements.mobilization.KotlinMetaModulePlugin>()
apply<nl.elements.mobilization.SigningMetaModulePlugin>()

apply(from = "gradle/jacoco.gradle")
