package com.theblackbit.rickandmorty

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.testConfig(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        @Suppress("UnstableApiUsage")
        testOptions {
            animationsDisabled = true
            unitTests {
                unitTests.isIncludeAndroidResources = true
                unitTests.isReturnDefaultValues = true
            }
        }

        dependencies {
            add("testImplementation", libsCatalog.findLibrary("junit").get())
            add("testImplementation", libsCatalog.findLibrary("mockk").get())
            add("testImplementation", libsCatalog.findLibrary("kotlinx.coroutines.test").get())
        }
    }
}
