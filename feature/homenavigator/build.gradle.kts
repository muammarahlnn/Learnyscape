plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.homenavigator"
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:schedule"))
    implementation(project(":feature:search"))

    implementation(libs.androidx.activity.compose)
}