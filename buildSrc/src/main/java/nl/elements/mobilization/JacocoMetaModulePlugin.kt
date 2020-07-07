package nl.elements.mobilization

import Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class JacocoMetaModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.subprojects {
            apply<JacocoSubprojectPlugin>()
        }


    }
}

class JacocoSubprojectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            apply(plugin = "jacoco")

            extensions.getByName<JacocoPluginExtension>("jacoco").apply {
                toolVersion = Versions.jacoco
            }

            tasks.create<JacocoReport>("jacocoCodeCoverageReport") {
                reports {
                    xml.isEnabled = true
                    html.isEnabled = true
                }
                val fileFilter = listOf(
                    "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*"
                )
                val debugTree = project.fileTree(
                    mapOf(
                        "dir" to "${project.buildDir}/intermediates/classes/debug",
                        "excludes" to fileFilter
                    )
                )
                val kotlinDebugTree = project.fileTree(
                    mapOf(
                        "dir" to "${project.buildDir}/tmp/kotlin-classes/debug",
                        "excludes" to fileFilter
                    )
                )
                val mainSrc = "${project.projectDir}/src/main/java"
                val sourceDirs = files(listOf(mainSrc))
                val classDirs = files(listOf(debugTree, kotlinDebugTree))

                sourceDirectories.from(sourceDirs)
                classDirectories.from(classDirs)

                executionData.from(
                    fileTree(
                        mapOf(
                            "dir" to project.buildDir.toString(),
                            "includes" to listOf("jacoco/testDebugUnitTest.exec")
                        )
                    )
                )
            }
        }
    }
}
