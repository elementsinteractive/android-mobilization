plugins {
    id("kotlin")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    api(Libs.Coroutines.core)
}

// Test dependencies
dependencies {
    testImplementation(Libs.Kotlin.stdlib)
    testImplementation(Libs.Kotlin.test)
    testImplementation(Libs.Kotlin.testJunit)
}
