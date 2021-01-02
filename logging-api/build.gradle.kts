plugins {
    id("kotlin")
    kotlin("kapt")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Kotlin.reflect)

    api(Libs.Coroutines.core)
}

// Test dependencies
dependencies {
    testImplementation(Libs.Kotlin.stdlib)
    testImplementation(Libs.Kotlin.test)
    testImplementation(Libs.Kotlin.testJunit)
}
