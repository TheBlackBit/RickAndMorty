package com.theblackbit.rickandmorty

import com.android.build.api.dsl.CommonExtension
import com.theblackbit.rickandmorty.Versions.compilerExtensionVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = compilerExtensionVersion
        }

        configurations.all {
            resolutionStrategy.force("androidx.compose.animation:animation:1.4.0")
        }

        dependencies {
            val bom = libsCatalog.findLibrary("compose.bom").get()
            add("implementation", platform(bom))
            add("implementation", libsCatalog.findLibrary("activity.compose").get())
            add("implementation", libsCatalog.findLibrary("compose.ui").get())
            add("implementation", libsCatalog.findLibrary("compose.ui.graphics").get())
            add("implementation", libsCatalog.findLibrary("compose.ui.tooling.preview").get())
            add("implementation", libsCatalog.findLibrary("compose.material3").get())
            add("implementation", libsCatalog.findLibrary("navigation.compose").get())
            add("implementation", libsCatalog.findLibrary("accompanist.navigation.animation").get())
            add("implementation", libsCatalog.findLibrary("constraintlayout.compose").get())
            add("androidTestImplementation", platform(bom))
            add("androidTestImplementation", libsCatalog.findLibrary("compose.ui.test.junit4").get())
            add("debugImplementation", libsCatalog.findLibrary("ui.tooling").get())
            add("debugImplementation", libsCatalog.findLibrary("ui.test.manifest").get())
        }
    }
}
