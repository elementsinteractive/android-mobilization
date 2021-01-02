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
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.4.20"

    id("com.diffplug.spotless") version Versions.spotless
    id("io.gitlab.arturbosch.detekt") version Versions.detekt

    id("com.vanniktech.android.junit.jacoco") version Versions.junitJacoco
    id("com.vanniktech.maven.publish") version Versions.mavenPublish
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
        force("org.jetbrains.kotlin:kotlin-reflect:${Libs.Kotlin.version}")
    }
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("src/**.kt")

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

signing {
    println("Setting up signing.")
    println("Has key password: ${project.hasProperty("SIGNINGPASSWORD")}")
    println("Has private key: ${project.hasProperty("SIGNING_PRIVATE_KEY")}")

    if (project.hasProperty("SIGNING_PRIVATE_KEY") &&
        project.hasProperty("SIGNINGPASSWORD")) {
        val privateKey = project.property("SIGNING_PRIVATE_KEY")
        val privateKeyPassword = project.property("SIGNINGPASSWORD")

        if (privateKey is String && privateKeyPassword is String) {
            useInMemoryPgpKeys(privateKey, privateKeyPassword)
            println("calling useInMemoryPgpKeys")
        }
    }
}
