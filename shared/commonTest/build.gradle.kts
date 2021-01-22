plugins {
    id("kotlin")
    id("java")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    implementation(Libs.Coroutines.core)

    implementation(Libs.Kotlin.testJunit)
    implementation(Libs.Coroutines.test)
}
