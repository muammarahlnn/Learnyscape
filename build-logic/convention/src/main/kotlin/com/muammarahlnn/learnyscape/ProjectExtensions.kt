package com.muammarahlnn.learnyscape

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ProjectExtensions, 09/07/2023 16.23 by Muammar Ahlan Abimanyu
 */
val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")