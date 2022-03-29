package nl.elements.mobilization

import BuildConfig
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.LibraryExtension
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

                if (this is LibraryPlugin) {
                    android.apply {
                        compileSdk = BuildConfig.compileSdk

                        defaultConfig {
                            minSdk = BuildConfig.minSdk
                            targetSdk = BuildConfig.targetSdk
                        }

                        lint {
                            textReport = true

                            // Disable lintVital. Not needed since lint is run on CI
                            checkReleaseBuilds = false
                            checkAllWarnings = true
                            checkDependencies = false
                            ignoreTestSources = true

                            htmlReport = true
                            htmlOutput = file("build/reports/lint-report.html")

                            disable += "GradleCompatible"
                        }

                        compileOptions {
                            sourceCompatibility = JavaVersion.VERSION_1_8
                            targetCompatibility = JavaVersion.VERSION_1_8
                        }
                    }
                }
            }
        }
    }
}
