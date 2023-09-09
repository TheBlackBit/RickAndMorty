import com.android.build.api.dsl.LibraryExtension
import com.theblackbit.rickandmorty.configureAndroidTest
import com.theblackbit.rickandmorty.configureKotlinAndroid
import com.theblackbit.rickandmorty.coreDependencies
import com.theblackbit.rickandmorty.testConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                    }

                    getByName("debug") {
                        enableUnitTestCoverage = true
                        enableAndroidTestCoverage = true
                    }
                }

                coreDependencies()
                testConfig(this)
                configureAndroidTest(this)
            }
        }
    }
}
