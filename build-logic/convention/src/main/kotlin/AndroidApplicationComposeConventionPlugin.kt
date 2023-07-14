import com.android.build.api.dsl.ApplicationExtension
import com.muammarahlnn.learnyscape.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AndroidApplicationComposeConventionPlugin, 09/07/2023 22.39 by Muammar Ahlan Abimanyu
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}