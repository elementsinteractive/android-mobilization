plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
}

android {
    // Library dependencies
    dependencies {
        implementation(libs.kotlin.stdlib)
        implementation(libs.kotlin.reflect)

        implementation(libs.androidx.appcompat)

        api(libs.kotlin.coroutines.core)

        implementation(libs.google.crashlytics)

        implementation(libs.timber)

        api(project(":logging-api"))


        // Test dependencies
        testImplementation(libs.kotlin.test.core)
        testImplementation(libs.kotlin.test.junit)
        testImplementation(libs.kotlin.coroutines.test)
        testImplementation(libs.androidx.test.core)
        testImplementation(libs.mockk)

        androidTestImplementation(libs.androidx.test.junit)
        androidTestImplementation(libs.androidx.test.rules)
        androidTestImplementation(libs.androidx.test.runner)

        androidTestImplementation(libs.espresso.core)
        androidTestImplementation(libs.espresso.contrib)
        androidTestImplementation(libs.espresso.intents)
        androidTestImplementation(libs.espresso.web)
    }
}
