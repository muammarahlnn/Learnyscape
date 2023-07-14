import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.muammarahlnn.learnyscape.configureKotlinAndroid
import com.muammarahlnn.learnyscape.disableUnnecessaryAndroidTests
import com.muammarahlnn.learnyscape.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AndroidLibraryConventionPlugin, 10/07/2023 17.12 by Muammar Ahlan Abimanyu
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }

            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit4").get())
                }
            }

            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
            }
        }
    }
}