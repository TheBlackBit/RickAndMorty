import com.theblackbit.rickandmorty.libsCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("kotlin-kapt")
            }

            dependencies {
                add("implementation", libsCatalog.findLibrary("hilt.android").get())
                add("kapt", libsCatalog.findLibrary("hilt.compiler").get())
                add("kapt", libsCatalog.findLibrary("hilt.android.compiler").get())
                add("kaptAndroidTest", libsCatalog.findLibrary("hilt.compiler").get())
            }
        }
    }
}
