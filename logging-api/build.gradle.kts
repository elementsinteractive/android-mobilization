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
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)

    api(libs.kotlin.coroutines.core)

    // Test dependencies
    testImplementation(libs.kotlin.test.core)
    testImplementation(libs.kotlin.test.junit)
}
