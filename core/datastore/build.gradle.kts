plugins {
    id("learnyscape.android.library")
    id("learnyscape.android.hilt")
}

android {
    namespace = "com.muammarahlnn.learnyscape.core.datastore"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)
}
