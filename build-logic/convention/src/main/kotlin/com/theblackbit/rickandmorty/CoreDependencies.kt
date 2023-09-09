package com.theblackbit.rickandmorty

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.coreDependencies() {
    dependencies {
        val bom = libsCatalog.findLibrary("kotlin.bom").get()
        add("implementation", platform(bom))
        add("implementation", libsCatalog.findLibrary("core.ktx").get())
        add("implementation", libsCatalog.findLibrary("lifecycle.runtime.ktx").get())
    }
}
