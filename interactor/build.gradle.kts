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
                artifactId = Mobilization.Artifacts.interactor
                version = Mobilization.version

                from(components["java"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(Mobilization.githubUrl)

            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
