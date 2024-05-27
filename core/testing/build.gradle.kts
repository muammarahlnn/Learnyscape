plugins {
    id("learnyscape.android.library")
    id("learnyscape.android.library.compose")
    id("learnyscape.android.hilt")
}

android {
    namespace = "com.muammarahlnn.learnyscape.core.testing"
}

dependencies {
    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)
    api(project(":core:data"))
    api(project(":core:model"))
    api(project(":core:ui"))

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.datetime)
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
}