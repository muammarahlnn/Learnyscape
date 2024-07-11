plugins {
    id("learnyscape.android.application")
    id("learnyscape.android.application.compose")
    id("learnyscape.android.hilt")
    id("learnyscape.android.application.firebase")
}

android {
    namespace = "com.muammarahlnn.learnyscape"

    defaultConfig {
        applicationId = "com.muammarahlnn.learnyscape"
        versionCode = 2
        versionName = "1.1.1"

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
    implementation(project(":feature:pendingrequest"))
    implementation(project(":feature:changepassword"))
    implementation(project(":feature:joinrequest"))
    implementation(project(":feature:resourcecreate"))
    implementation(project(":feature:submissiondetails"))

    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))

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

    testImplementation(project(":core:testing"))
    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.hilt.android.testing)

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.hilt.android.testing)
}