package nl.elements.mobilization

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

class SigningMetaModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.subprojects {
            plugins.whenPluginAdded {
                if (this is SigningPlugin) {
                    extensions.findByType<SigningExtension>()?.apply {
                        if (project.hasProperty("SIGNING_PRIVATE_KEY") &&
                            project.hasProperty("SIGNINGPASSWORD")
                        ) {
                            val privateKey = project.property("SIGNING_PRIVATE_KEY") as String
                            val privateKeyPassword = project.property("SIGNINGPASSWORD") as String

                            val username = project.property("SONATYPE_NEXUS_USERNAME") as String

                            println("username: $username")
                            println("Has private key and password")

                            useInMemoryPgpKeys(privateKey, privateKeyPassword)
                        }
                    }
                }

            }
        }
    }
}
