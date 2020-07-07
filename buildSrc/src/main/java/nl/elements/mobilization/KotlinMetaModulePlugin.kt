package nl.elements.mobilization

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinMetaModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.subprojects {
            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_1_8.toString()

                    // Treat all Kotlin warnings as errors
                    allWarningsAsErrors = true

                    // Enable experimental coroutines APIs, including Flow
                    freeCompilerArgs += listOf(
                        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                        "-Xuse-experimental=kotlinx.coroutines.FlowPreview"
                    )
                }
            }

            plugins.whenPluginAdded {
                if (this is Kapt3GradleSubplugin) {
                    extensions.findByType<KaptExtension>()?.apply {
                        correctErrorTypes = true
                        useBuildCache = true
                    }
                }
            }
        }
    }
}
