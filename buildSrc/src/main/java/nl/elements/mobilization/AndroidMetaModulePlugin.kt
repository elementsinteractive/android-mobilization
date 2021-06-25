package nl.elements.mobilization

import BuildConfig
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

class AndroidMetaModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.subprojects {
            plugins.whenPluginAdded {
                if (this is KotlinAndroidPluginWrapper) {
                    extensions.findByType<AndroidExtensionsExtension>()?.apply {
                        isExperimental = true
                    }
                }

                if (this is AppPlugin || this is LibraryPlugin) {
                    android.apply {
                        compileSdkVersion(BuildConfig.compileSdk)

                        defaultConfig {
                            minSdk = BuildConfig.minSdk
                            targetSdk = BuildConfig.targetSdk
                        }

                        lintOptions {
                            textReport = true
                            textOutput("stdout")

                            isCheckReleaseBuilds = false
                            isCheckAllWarnings = true
                            htmlReport = true
                            htmlOutput = file("build/reports/lint-report.html")

                            // Disable lintVital. Not needed since lint is run on CI
                            isCheckReleaseBuilds = false
                            // Allow lint to check dependencies
                            isCheckDependencies = false
                            // Ignore any tests
                            isIgnoreTestSources = true

                            disable("GradleCompatible")
                        }

                        compileOptions {
                            sourceCompatibility = JavaVersion.VERSION_1_8
                            targetCompatibility = JavaVersion.VERSION_1_8
                        }

                        sourceSets {
                            getByName("main").java.apply {
                                setSrcDirs(srcDirs + file("src/main/kotlin"))
                            }
                            getByName("test").java.apply {
                                setSrcDirs(srcDirs + file("src/test/kotlin"))
                            }
                        }
                    }
                }
            }
        }
    }
}
