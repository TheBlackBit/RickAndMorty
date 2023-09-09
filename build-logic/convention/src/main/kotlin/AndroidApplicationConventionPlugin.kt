import com.android.build.api.dsl.ApplicationExtension
import com.theblackbit.rickandmorty.Versions
import com.theblackbit.rickandmorty.configureAndroidTest
import com.theblackbit.rickandmorty.configureBuildTypes
import com.theblackbit.rickandmorty.configureKotlinAndroid
import com.theblackbit.rickandmorty.configureSigningConfigs
import com.theblackbit.rickandmorty.coreDependencies
import com.theblackbit.rickandmorty.testConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = Versions.targetSdk
                defaultConfig.vectorDrawables.useSupportLibrary = true
                configureSigningConfigs(target)
                configureBuildTypes()
                configureKotlinAndroid(this)
                testConfig(this)
                configureAndroidTest(this)
                coreDependencies()
            }
        }
    }
}
