plugins {
    id("learnyscape.android.library")
    id("learnyscape.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.muammarahlnn.learnyscape.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)
}