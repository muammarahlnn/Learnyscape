plugins {
    id("learnyscape.android.feature")
    id("learnyscape.android.library.compose")
}

android {
    namespace = "com.muammarahlnn.learnyscape.feature.pendingrequest"
}

dependencies {

    implementation(libs.androidx.activity.compose)
}