plugins {
    id("kotlin")
    id("java")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    implementation(Libs.Coroutines.core)
}

// Test dependencies
dependencies {
    testImplementation(Libs.Kotlin.stdlib)
    testImplementation(Libs.Kotlin.test)
    testImplementation(Libs.Kotlin.testJunit)
    testImplementation(Libs.Coroutines.test)

    testImplementation(project(":shared:commonTest"))
}
