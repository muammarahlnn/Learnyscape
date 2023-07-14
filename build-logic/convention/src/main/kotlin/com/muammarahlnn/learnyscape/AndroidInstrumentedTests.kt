package com.muammarahlnn.learnyscape

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AndroidInstrumentedTests, 09/07/2023 20.22 by Muammar Ahlan Abimanyu
 */
internal fun LibraryAndroidComponentsExtension.disableUnnecessaryAndroidTests(project: Project) {
    beforeVariants {
        it.enableAndroidTest = it.enableAndroidTest && project.projectDir.resolve("src/androidTests").exists()
    }
}