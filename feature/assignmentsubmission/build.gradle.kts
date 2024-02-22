plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.assignmentsubmission"
}

dependencies {

    implementation(libs.androidx.activity.compose)
}