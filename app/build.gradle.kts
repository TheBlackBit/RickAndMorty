plugins {
    id("theblackbit.rickandmorty.application")
    id("theblackbit.rickandmorty.application.compose")
    id("theblackbit.rickandmorty.hilt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.theblackbit.rickandmorty"

    defaultConfig {
        applicationId = "com.theblackbit.rickandmorty"
        versionCode = 1
        versionName = "0.0.0"
    }
}
