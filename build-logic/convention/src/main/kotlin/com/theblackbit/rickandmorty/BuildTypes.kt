package com.theblackbit.rickandmorty

import com.android.build.api.dsl.ApplicationExtension
import com.theblackbit.rickandmorty.SingingConfigs.RELEASE

internal fun ApplicationExtension.configureBuildTypes() {
    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            this.signingConfig = signingConfigs.getByName(RELEASE)
        }

        getByName("debug") {
            isDebuggable = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
    }
}
