import org.gradle.kotlin.dsl.DependencyHandlerScope

object Extensions {

    fun DependencyHandlerScope.apis(dependencies: List<String>) = dependencies.forEach { dependencyNotation ->
        add("api", dependencyNotation)
    }

    fun DependencyHandlerScope.implementations(dependencies: List<String>) = dependencies.forEach { dependencyNotation ->
        add("implementation", dependencyNotation)
    }

    fun DependencyHandlerScope.testImplementations(dependencies: List<String>) = dependencies.forEach { dependencyNotation ->
        add("testImplementation", dependencyNotation)
    }

    fun DependencyHandlerScope.androidTestImplementations(dependencies: List<String>) = dependencies.forEach { dependencyNotation ->
        add("androidTestImplementation", dependencyNotation)
    }
}
