import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.4.32"

    id("com.diffplug.spotless") version "6.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.17.1"

    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
    id("com.vanniktech.maven.publish") version "0.18.0" apply false
}

repositories {
    mavenCentral()
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
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

        allRules = true
        buildUponDefaultConfig = true
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
