plugins {
    id("theblackbit.rickandmorty.library")
    id("theblackbit.rickandmorty.library.compose")
    id("theblackbit.rickandmorty.hilt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.theblackbit.rickandmorty.core.testing"
}

dependencies {
    api(libs.testing.core)
    api(libs.testing.runner)
    api(libs.testing.rules)
    implementation(libs.hilt.android.testing)
}
