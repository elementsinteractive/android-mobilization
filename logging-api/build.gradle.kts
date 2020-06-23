plugins {
    id("kotlin")
    kotlin("kapt")
    id("maven-publish")
}

dependencies {
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = Mobilisation.groupId
                artifactId = Mobilisation.Artifacts.loggingApi
                version = Mobilisation.version

                from(components["java"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(Mobilisation.githubUrl)
        }
    }
}
