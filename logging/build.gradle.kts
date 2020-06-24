import Extensions.androidTestImplementations

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("maven-publish")
}

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

        implementation(Libs.Google.firebaseCrashlytics)

        implementation(Libs.timber)

        implementation(project(":logging-api"))
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

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("gpr") {
                run {
                    groupId = Mobilization.groupId
                    artifactId = Mobilization.Artifacts.logging
                    version = Mobilization.version

                    artifact("$buildDir/outputs/aar/${Mobilization.Artifacts.logging}-release.aar")
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri(Mobilization.githubUrl)

                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
