import com.muammarahlnn.learnyscape.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AndroidRoomConventionPlugin, 10/07/2023 17.32 by Muammar Ahlan Abimanyu
 */
class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            dependencies {
                add("implementation", libs.findLibrary("room.runtime").get())
                add("implementation", libs.findLibrary("room.ktx").get())
                add("ksp", libs.findLibrary("room.compiler").get())
            }
        }
    }
}