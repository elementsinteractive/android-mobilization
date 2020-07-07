plugins {
    id("kotlin")
    kotlin("kapt")
    id("maven-publish")
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    implementation(Libs.Coroutines.core)

    implementation(Libs.Kotlin.testJunit)
    implementation(Libs.Coroutines.test)
}
