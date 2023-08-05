plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.quiz"
}

dependencies {

    implementation(libs.androidx.activity.compose)
}