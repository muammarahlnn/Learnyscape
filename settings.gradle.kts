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
include(":core:model")
include(":core:common")

include(":feature:home")
include(":feature:search")
include(":feature:profile")
include(":feature:schedule")
include(":feature:notifications")
include(":feature:class")
include(":feature:module")
include(":feature:assignment")
include(":feature:quiz")
include(":feature:member")
include(":feature:resourcedetails")
include(":feature:quizsession")
include(":feature:homenavigator")
include(":feature:classnavigator")
include(":feature:login")
include(":feature:camera")
