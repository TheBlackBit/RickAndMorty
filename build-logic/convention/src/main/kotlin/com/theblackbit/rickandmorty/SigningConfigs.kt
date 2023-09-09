package com.theblackbit.rickandmorty

import com.android.build.api.dsl.ApplicationExtension
import com.theblackbit.rickandmorty.SingingConfigs.RELEASE
import org.gradle.api.Project
import java.io.FileInputStream
import java.util.Properties

internal fun ApplicationExtension.configureSigningConfigs(project: Project) {
    val propertiesFile = project.rootProject.file("secrets.defaults.properties")
    val keyStoreFile = project.rootProject.file("${project.projectDir}/keystore.jks")

    val properties = Properties()

    properties.load(FileInputStream(propertiesFile))

    signingConfigs {
        signingConfigs.create(RELEASE) {
            storeFile = keyStoreFile
            storePassword = properties.getProperty("KEY_PASS")
            keyAlias = properties.getProperty("ALIAS")
            keyPassword = properties.getProperty("KEY_ALIAS")
            enableV1Signing = true
            enableV2Signing = true
        }
    }
}
