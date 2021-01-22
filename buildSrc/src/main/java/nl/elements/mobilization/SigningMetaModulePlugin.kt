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
                            val privateKey = project.property("SIGNING_PRIVATE_KEY")
                            val privateKeyPassword = project.property("SIGNINGPASSWORD")

                            if (privateKey is String && privateKeyPassword is String) {
                                useInMemoryPgpKeys(privateKey, privateKeyPassword)
                            }
                        }
                    }
                }
            }
        }
    }
}
