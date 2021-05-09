object BuildConfig {
    const val compileSdk = 30
    const val minSdk = 23
    const val targetSdk = 30
}


object Libs {

    object Kotlin {
        const val version = "1.4.32"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
        const val test = "org.jetbrains.kotlin:kotlin-test:$version"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Coroutines {
        private const val version = "1.4.3"
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
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.3.0"
    }

    object AndroidX {

        const val appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"

        object Test {
            private const val version = "1.3.1-alpha03"

            const val core = "androidx.test:core-ktx:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            private const val junitVersion = "1.1.3-alpha03"

            const val junit =  "androidx.test.ext:junit:$junitVersion"
            const val junitKtx =  "androidx.test.ext:junit-ktx:$junitVersion"

            object Espresso {
                private const val version = "3.4.0-alpha03"
                private const val espressoContrib = "androidx.test.espresso:espresso-contrib:$version"
                private const val espressoCore = "androidx.test.espresso:espresso-core:$version"
                private const val espressoIntents = "androidx.test.espresso:espresso-intents:$version"
                private const val espressoWeb = "androidx.test.espresso:espresso-web:$version"

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

    const val mockk = "io.mockk:mockk:1.10.5"
}
