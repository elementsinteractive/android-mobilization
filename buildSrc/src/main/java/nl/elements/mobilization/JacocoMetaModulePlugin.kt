package nl.elements.mobilization

import Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class JacocoMetaModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            apply(plugin = "jacoco")

            extensions.getByName<JacocoPluginExtension>("jacoco").apply {
                toolVersion = Versions.jacoco
            }

            subprojects {
                println("Applying Jacoco to $name")
                apply(plugin = "jacoco")
            }

            afterEvaluate {
                tasks.create<JacocoReport>("jacocoTestCoverageReport") {
                    group = "Reporting"
                    description = "Generate Jacoco coverage reports"

                    reports {
                        html.isEnabled = true
                        xml.isEnabled = true
                        csv.isEnabled = false
                    }

                    val fileFilter = listOf(
                        "**/android/*",
                        "**/inject/*",
                        "**/R.class",
                        "**/R$*.class",
                        "**/BuildConfig.*",
                        "**/Manifest*.*",
                        "**/*Test*.*",
                        "**/android/databinding/*",
                        "**/androidx/databinding/*",
                        "**/*MapperImpl*.*",
                        "**/*\$ViewInjector*.*",
                        "**/*\$ViewBinder*.*",
                        "**/BuildConfig.*",
                        "**/*Component*.*",
                        "**/*BR*.*",
                        "**/*\$Lambda$*.*",
                        "**/*Companion*.*",
                        "**/*Module.*",
                        "**/*Dagger*.*",
                        "**/*_Factory*.*",
                        "**/*_Provide*Factory*.*",
                        "**/*Extensions*.*",
                        "**/*\$Result.*", /* filtering `sealed` and `data` classes */
                        "**/*\$Result$*.*", /* filtering `sealed` and `data` classes */
                        "**/*Module*"
                    )

                    val classDirs = listOf(
                        fileTree("${project.rootDir}/logging/build/tmp/kotlin-classes/debug") {
                            exclude(fileFilter)
                        },
                        fileTree("${project.rootDir}/logging-api/build/classes/kotlin") {
                            exclude(fileFilter)
                        }
                    )

                    val executionDataDirs = listOf(
                        "${project.rootDir}/logging/build/jacoco/testDebugUnitTest.exec"
                    )

                    val coverageSourceDirs = listOf(
                        "${project.rootDir}/logging/src/main/java",
                        "${project.rootDir}/logging-api/src/main/java"
                    )

                    println(classDirs)
                    println(executionDataDirs)
                    println(coverageSourceDirs)

                    classDirectories.setFrom(files(classDirs))
                    additionalSourceDirs.setFrom(files(coverageSourceDirs))
                    sourceDirectories.setFrom(files(coverageSourceDirs))
                    executionData(files(executionDataDirs))
                }
            }
        }
    }
}
