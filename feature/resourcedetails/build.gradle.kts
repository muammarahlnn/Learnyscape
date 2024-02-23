plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.resourcedetails"
}

dependencies {
    implementation(project(":feature:assignmentsubmission"))
    implementation(project(":feature:studentwork"))

    implementation(libs.androidx.activity.compose)
}