plugins {
    `kotlin-dsl`
    java
}

repositories {
    google()
    maven(url = "https://plugins.gradle.org/m2/")
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:7.1.0-alpha02")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
}
