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
    implementation(libs.kotlin.stdlib)

    api(libs.kotlin.coroutines.core)

    implementation(libs.kotlin.test.junit)
    implementation(libs.kotlin.coroutines.test)
}
