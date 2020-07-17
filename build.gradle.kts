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
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)
        classpath(Libs.mavenPublishGradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version Versions.spotless
    id("io.gitlab.arturbosch.detekt") version Versions.detekt

    id("com.vanniktech.android.junit.jacoco") version "0.16.0"
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

subprojects {
    apply(plugin = "com.diffplug.gradle.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            ktlint(Versions.ktlint)
            licenseHeaderFile(rootProject.file("spotless/license.kt"))
        }
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        version = Versions.detekt
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

apply(from = "gradle/jacoco.gradle")
