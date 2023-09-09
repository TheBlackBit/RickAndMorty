import com.theblackbit.rickandmorty.libsCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidPagingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libsCatalog.findLibrary("paging.runtime.ktx").get())
                add("implementation", libsCatalog.findLibrary("paging.compose").get())
                add("testImplementation", libsCatalog.findLibrary("paging.common.ktx").get())
            }
        }
    }
}
