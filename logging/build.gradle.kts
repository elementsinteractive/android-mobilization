import Extensions.androidTestImplementations

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
}

android {
    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    // Library dependencies
    dependencies {
        implementation(Libs.Kotlin.stdlib)

        implementation(Libs.Google.firebaseCrashlytics)

        implementation(Libs.timber)

        api(project(":logging-api"))
    }

    // Test dependencies
    dependencies {
        testImplementation(Libs.Kotlin.stdlib)
        testImplementation(Libs.Kotlin.test)
        testImplementation(Libs.Kotlin.testJunit)
        testImplementation(Libs.Coroutines.test)
        testImplementation(Libs.AndroidX.Test.core)
        testImplementation(Libs.mockk)

        androidTestImplementation(Libs.AndroidX.Test.junit)
        androidTestImplementation(Libs.AndroidX.Test.junitKtx)
        androidTestImplementation(Libs.AndroidX.Test.rules)
        androidTestImplementation(Libs.AndroidX.Test.runner)

        androidTestImplementations(Libs.AndroidX.Test.Espresso.dependencies)
    }
}
