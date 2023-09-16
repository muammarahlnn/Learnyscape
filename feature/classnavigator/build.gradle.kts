plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.classnavigator"
}

dependencies {
    implementation(project(":feature:class"))
    implementation(project(":feature:module"))
    implementation(project(":feature:assignment"))
    implementation(project(":feature:quiz"))
    implementation(project(":feature:member"))

    implementation(libs.androidx.activity.compose)
}