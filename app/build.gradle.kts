plugins {
    id("learnyscape.android.application")
    id("learnyscape.android.application.compose")
    id("learnyscape.android.hilt")
}

android {
    namespace = "com.muammarahlnn.learnyscape"

    defaultConfig {
        applicationId = "com.muammarahlnn.learnyscape"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":feature:homenavigator"))
    implementation(project(":feature:classnavigator"))
    implementation(project(":feature:notifications"))
    implementation(project(":feature:resourcedetails"))
    implementation(project(":feature:quizsession"))
    implementation(project(":feature:login"))
    implementation(project(":feature:camera"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:model"))

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.coil.kt)
}