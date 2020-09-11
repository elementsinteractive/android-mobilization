object BuildConfig {
    const val compileSdk = 29
    const val minSdk = 23
    const val targetSdk = 29
}

object Versions {
    const val ktlint = "0.40.0"
    const val spotless = "5.8.2"
    const val detekt = "1.14.2"
    const val jacoco = "0.8.6"
    const val junitJacoco = "0.16.0"
    const val mavenPublish = "0.13.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.1.1"
    const val mavenPublishGradlePlugin = "com.vanniktech:gradle-maven-publish-plugin:${Versions.mavenPublish}"

    object Kotlin {
        const val version = "1.4.21-2"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val test = "org.jetbrains.kotlin:kotlin-test:$version"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Coroutines {
        private const val version = "1.4.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"

        val dependencies = listOf(
            core,
            android
        )
    }

    object Google {
        // Android
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.0.0"
    }

    object AndroidX {
        object Test {
            private const val version = "1.3.1-alpha02"

            const val core = "androidx.test:core-ktx:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            private const val junitVersion = "1.1.2-beta01"

            const val junit =  "androidx.test.ext:junit:$junitVersion"
            const val junitKtx =  "androidx.test.ext:junit-ktx:$junitVersion"

            object Espresso {
                private const val version = "3.4.0-alpha02"
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

    const val mockk = "io.mockk:mockk:1.10.3"
}
