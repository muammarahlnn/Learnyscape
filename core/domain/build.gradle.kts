plugins {
    id("learnyscape.android.library")
    kotlin("kapt")
}

android {
    namespace = "com.muammarahlnn.learnyscape.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    kapt(libs.hilt.compiler)
}