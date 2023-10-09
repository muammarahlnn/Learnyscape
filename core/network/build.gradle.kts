plugins {
    id("learnyscape.android.library")
    id("learnyscape.android.hilt")
    id("kotlinx-serialization")
}

// gradle.properties variables
val BASE_URL: String by project

android {
    namespace = "com.muammarahlnn.learnyscape.core.network"
    buildFeatures {
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    defaultConfig {
        buildConfigField("String", "BASE_URL", BASE_URL)
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}
