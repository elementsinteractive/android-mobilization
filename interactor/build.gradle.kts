plugins {
    id("kotlin")
    id("org.jetbrains.dokka")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)

    implementation(libs.kotlin.coroutines.core)


    // Test dependencies
    testImplementation(libs.kotlin.test.core)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlin.coroutines.test)

    testImplementation(project(":shared:commonTest"))
}
