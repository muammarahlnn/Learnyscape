pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Learnyscape"
include(":app")
include(":core:designsystem")
include(":core:ui")

include(":feature:home")
include(":feature:search")
include(":feature:profile")
include(":feature:schedule")
