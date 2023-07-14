import com.muammarahlnn.learnyscape.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file JvmLibraryConventionPlugin, 10/07/2023 18.13 by Muammar Ahlan Abimanyu
 */
class JvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
            }
            configureKotlinJvm()
        }
    }
}