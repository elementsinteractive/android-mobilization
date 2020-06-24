plugins {
    id("kotlin")
    kotlin("kapt")
    id("maven-publish")
}

dependencies {
    implementation(Libs.Kotlin.stdlib)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = Mobilization.groupId
                artifactId = Mobilization.Artifacts.loggingApi
                version = Mobilization.version

                from(components["java"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(Mobilization.githubUrl)
        }
    }
}
