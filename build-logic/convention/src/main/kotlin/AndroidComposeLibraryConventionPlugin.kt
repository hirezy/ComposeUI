import com.android.build.api.dsl.LibraryExtension
import com.hirezy.composeuI.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidComposeLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<LibraryExtension>().apply {
                defaultConfig {
                    consumerProguardFile("consumer-rules.pro")
                }
            }
            configureAndroidCompose(extension)
        }
    }
}