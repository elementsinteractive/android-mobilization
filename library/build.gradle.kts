import Extensions.androidTestImplementations

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply("gradle/jacoco.gradle")

android {
    defaultConfig {
        versionCode = 1
        versionName = Mobilization.version
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    // Library dependencies
    dependencies {
        implementation(Libs.Kotlin.stdlib)
    }

    // Test dependencies
    dependencies {
        testImplementation(Libs.Kotlin.stdlib)
        testImplementation(Libs.Kotlin.test)
        testImplementation(Libs.Kotlin.testJunit)
        testImplementation(Libs.Coroutines.test)
        testImplementation(Libs.AndroidX.Test.core)

        androidTestImplementation(Libs.AndroidX.Test.junit)
        androidTestImplementation(Libs.AndroidX.Test.junitKtx)
        androidTestImplementation(Libs.AndroidX.Test.rules)
        androidTestImplementation(Libs.AndroidX.Test.runner)

        androidTestImplementations(Libs.AndroidX.Test.Espresso.dependencies)
    }
}
