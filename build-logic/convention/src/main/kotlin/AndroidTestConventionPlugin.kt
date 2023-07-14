import com.android.build.gradle.TestExtension
import com.muammarahlnn.learnyscape.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AndroidTestConventionPlugin, 10/07/2023 18.11 by Muammar Ahlan Abimanyu
 */
class AndroidTestConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 31
            }
        }
    }
}