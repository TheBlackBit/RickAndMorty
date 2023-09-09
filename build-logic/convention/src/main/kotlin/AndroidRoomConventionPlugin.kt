import com.theblackbit.rickandmorty.libsCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
            }

            extensions.configure<KaptExtension> {
                arguments {
                    arg("room.schemaLocation", "$projectDir/schemas")
                }
            }

            dependencies {
                add("implementation", libsCatalog.findLibrary("room.ktx").get())
                add("implementation", libsCatalog.findLibrary("room.guava").get())
                add("implementation", libsCatalog.findLibrary("room.runtime").get())
                add("androidTestImplementation", libsCatalog.findLibrary("room.testing").get())
                add("kapt", libsCatalog.findLibrary("room.compiler").get())
            }
        }
    }
}
