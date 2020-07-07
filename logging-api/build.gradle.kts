plugins {
    id("kotlin")
    kotlin("kapt")
    id("com.vanniktech.maven.publish")
}

dependencies {
    implementation(Libs.Kotlin.stdlib)
}
