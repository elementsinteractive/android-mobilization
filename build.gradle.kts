import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.diffplug.gradle.spotless") version Versions.spotless
    id("io.gitlab.arturbosch.detekt") version Versions.detekt
}

apply<nl.elements.mobilization.AndroidMetaModulePlugin>()
apply<nl.elements.mobilization.KotlinMetaModulePlugin>()

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    apply(plugin = "com.diffplug.gradle.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            ktlint(Versions.ktlint)
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

val detektCheck by tasks.registering(Detekt::class) {
    description = "Runs a failfast detekt build."
    setSource(files(
        "$projectDir/library/src",
        "$projectDir/logging-api/src"
    ))
    config.setFrom("$rootDir/detekt.yml")

    include("**/*.kt")
    include("**/*.kts")
    exclude(".*test.*")
    exclude(".*/resources/.*")
    exclude(".*/tmp/.*")
    exclude(".*/res/.*")
}
