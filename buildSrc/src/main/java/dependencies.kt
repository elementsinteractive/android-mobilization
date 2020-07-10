object BuildConfig {
    const val compileSdk = 29
    const val minSdk = 23
    const val targetSdk = 29
}

object Versions {
    const val ktlint = "0.34.2"
    const val spotless = "3.30.0"
    const val detekt = "1.8.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.0"
    const val mavenPublishGradlePlugin = "com.vanniktech:gradle-maven-publish-plugin:0.11.1"

    object Kotlin {
        private const val version = "1.3.72"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val test = "org.jetbrains.kotlin:kotlin-test:$version"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
    }

    object Coroutines {
        private const val version = "1.3.7"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"

        val dependencies = listOf(
            core,
            rx2,
            android
        )
    }

    object Google {
        // Android
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.0.0"
    }

    object AndroidX {
        object Test {
            private const val version = "1.3.0-beta01"

            const val core = "androidx.test:core-ktx:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            private const val junitVersion = "1.1.2-beta01"

            const val junit =  "androidx.test.ext:junit:$junitVersion"
            const val junitKtx =  "androidx.test.ext:junit-ktx:$junitVersion"

            object Espresso {
                private const val version = "3.3.0-beta01"
                const val espressoContrib = "androidx.test.espresso:espresso-contrib:$version"
                const val espressoCore = "androidx.test.espresso:espresso-core:$version"
                const val espressoIntents = "androidx.test.espresso:espresso-intents:$version"
                const val espressoWeb = "androidx.test.espresso:espresso-web:$version"

                val dependencies = listOf(
                    espressoContrib,
                    espressoCore,
                    espressoIntents,
                    espressoWeb
                )
            }
        }
    }

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val mockk = "io.mockk:mockk:1.9.3"
}
