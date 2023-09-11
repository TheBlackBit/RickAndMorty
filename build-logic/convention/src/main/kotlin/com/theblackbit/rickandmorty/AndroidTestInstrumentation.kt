package com.theblackbit.rickandmorty

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidTest(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        defaultConfig.testInstrumentationRunner = "com.theblackbit.rickandmorty.core.testing.HiltTestRunner"
    }

    dependencies {
        add("androidTestImplementation", libsCatalog.findLibrary("espresso.core").get())
        add("androidTestImplementation", libsCatalog.findLibrary("espresso.contrib").get())
        add("androidTestImplementation", libsCatalog.findLibrary("espresso.intent").get())
        add("androidTestImplementation", libsCatalog.findLibrary("espresso.web").get())
        "androidTestImplementation"(project(":core:testing"))
        add("androidTestImplementation", libsCatalog.findLibrary("android.test.junit").get())
    }
}
