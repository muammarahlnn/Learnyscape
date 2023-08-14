plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.aclass"
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
}