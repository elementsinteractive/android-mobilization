package nl.elements.mobilization

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

val Project.android: LibraryExtension
    get() = project.extensions.getByName("android") as LibraryExtension
