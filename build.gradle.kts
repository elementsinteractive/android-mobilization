import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                // detekt needs 'kotlinx-html' for the html report
                // just allow to include kotlinx projects
                includeGroup("org.jetbrains.kotlinx")
            }
        }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.13.0")
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.4.32"

    id("com.diffplug.spotless") version "5.12.4"
    id("io.gitlab.arturbosch.detekt") version "1.15.0"

    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
    id("com.vanniktech.maven.publish") version "0.13.0" apply false
}

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx.html/") }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlinx.html/") }
    }
}

configurations.all {
    resolutionStrategy {
        force(rootProject.libs.kotlin.reflect)
    }
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("src/**.kt")

            ktlint(rootProject.libs.versions.ktlint.get())

            licenseHeaderFile(rootProject.file("spotless/license.kt"))
        }
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        version = rootProject.libs.versions.detekt
        input = files("$projectDir/src")
        config = files("$rootDir/detekt.yml")

        failFast = true
        autoCorrect = false
    }
}

tasks.create<Detekt>("detektCheck") {
    description = "Runs a failfast detekt build."
    setSource(files(
        "$projectDir/interactor/src",
        "$projectDir/logging-api/src",
        "$projectDir/logging/src"
    ))
    config.setFrom("$rootDir/detekt.yml")

    include("**/*.kt")
    include("**/*.kts")
    exclude(".*test.*")
    exclude(".*/resources/.*")
    exclude(".*/tmp/.*")
    exclude(".*/res/.*")
}

apply<nl.elements.mobilization.AndroidMetaModulePlugin>()
apply<nl.elements.mobilization.KotlinMetaModulePlugin>()
apply<nl.elements.mobilization.SigningMetaModulePlugin>()

apply(from = "gradle/jacoco.gradle")
