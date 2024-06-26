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
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:datastore")

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
include(":feature:pendingrequest")
include(":feature:changepassword")
include(":feature:joinrequest")
include(":feature:resourcecreate")
include(":feature:submissiondetails")
include(":feature:assignmentsubmission")
include(":feature:studentwork")
include(":core:testing")
