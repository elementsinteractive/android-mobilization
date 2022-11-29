plugins {
    `kotlin-dsl`
    java
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:7.1.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
}
