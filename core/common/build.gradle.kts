plugins {
    id("learnyscape.android.library")
    id("learnyscape.android.hilt")
}

android {
    namespace = "com.muammarahlnn.learnyscape.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}