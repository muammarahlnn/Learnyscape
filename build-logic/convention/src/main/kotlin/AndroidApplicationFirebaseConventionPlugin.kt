import com.muammarahlnn.learnyscape.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AndroidFirebaseConventionPlugin, 13/05/2024 17.52
 */
class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                "implementation"(libs.findLibrary("firebase-messaging").get())
            }
        }
    }
}