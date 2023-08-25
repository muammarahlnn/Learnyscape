plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.quizsession"
}

dependencies {

    implementation(libs.androidx.activity.compose)
}